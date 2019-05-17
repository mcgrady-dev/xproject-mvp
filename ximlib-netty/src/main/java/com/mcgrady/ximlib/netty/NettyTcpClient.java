package com.mcgrady.ximlib.netty;

import android.text.TextUtils;
import android.util.Log;

import com.mcgrady.ximlib.ExecutorServiceFactory;
import com.mcgrady.ximlib.IMSConfig;
import com.mcgrady.ximlib.MsgDispatcher;
import com.mcgrady.ximlib.MsgTimeoutTimerManager;
import com.mcgrady.ximlib.handler.HeartbeatHandler;
import com.mcgrady.ximlib.handler.TCPChannelInitializerHandler;
import com.mcgrady.ximlib.handler.TCPReadHandler;
import com.mcgrady.ximlib.interf.IMSClientInterface;
import com.mcgrady.ximlib.interf.IMSConnectStatusCallback;
import com.mcgrady.ximlib.interf.OnEventListener;
import com.mcgrady.ximlib.proto.MessageProtobuf;

import java.util.Vector;
import java.util.concurrent.TimeUnit;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by mcgrady on 2019/5/17.
 */
public class NettyTcpClient implements IMSClientInterface {

    private final String TAG = NettyTcpClient.class.getSimpleName();

    private static volatile NettyTcpClient instance;

    private Bootstrap bootstrap;
    private Channel channel;

    private boolean isClosed;
    private Vector<String> serverUrlList;
    private OnEventListener onEventListener;
    private IMSConnectStatusCallback imsConnectStatusCallback;
    private MsgDispatcher msgDispather;
    private ExecutorServiceFactory loopGroup;
    private MsgTimeoutTimerManager msgTimeoutTimerManager;
    private boolean isReconnecting;
    private int connectStatus;
    private String currentHost;
    private Object currentPort = -1;
    private int appStatus;
    private int foregroundHeartbeatInterval;
    private int backgroundHeartbeatInterval;
    private int heartbeatInterval;

    private NettyTcpClient() {

    }

    public static NettyTcpClient getInstance() {
        if (null == instance) {
            synchronized (NettyTcpClient.class) {
                if (null == instance) {
                    instance = new NettyTcpClient();
                }
            }
        }

        return instance;
    }

    @Override
    public void init(Vector<String> serverUrlList, OnEventListener listener, IMSConnectStatusCallback callback) {
        close();
        isClosed = false;
        this.serverUrlList = serverUrlList;
        this.onEventListener = listener;
        this.imsConnectStatusCallback = callback;
        msgDispather = new MsgDispatcher();
        msgDispather.setOnEventListener(listener);
        loopGroup = new ExecutorServiceFactory();
        loopGroup.initBossLoopGroup();      //初始化重连线程组
        msgTimeoutTimerManager = new MsgTimeoutTimerManager(this);

        resetConnect(true);
    }

    @Override
    public void resetConnect() {
        this.resetConnect(false);
    }

    @Override
    public void resetConnect(boolean isFirst) {
        if (!isFirst) {
            try {
                Thread.sleep(IMSConfig.DEFAULT_RECONNECT_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!isClosed && !isReconnecting) {
            synchronized (this) {
                if (!isClosed && !isReconnecting) {
                    // 标识正在进行重连
                    isReconnecting = true;
                    // 回调ims连接状态
                    onConnectStatusCallback(IMSConfig.CONNECT_STATE_CONNECTING);
                    // 先关闭channel
                    closeChannel();
                    // 执行重连任务
                    loopGroup.execBossTask(new ResetConnectRunnable(isFirst));
                }
            }
        }
    }

    private void closeChannel() {
        try {
            if (channel != null) {
                channel.close();
                channel.eventLoop().shutdownGracefully();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "close channel failure, reason:" + e.getMessage());
        } finally {
            channel = null;
        }
    }

    private void onConnectStatusCallback(int connectStatus) {
        this.connectStatus = connectStatus;
        switch (connectStatus) {
            case IMSConfig.CONNECT_STATE_CONNECTING:
                Log.d(TAG, "IMService connecting...");
                if (imsConnectStatusCallback != null) {
                    imsConnectStatusCallback.onConnecting();
                }
                break;
            case IMSConfig.CONNECT_STATE_SUCCESSFUL:
                Log.d(TAG, String.format("IMService connect successful, host:%s, port:%s", currentHost, currentPort));
                if (imsConnectStatusCallback != null) {
                    imsConnectStatusCallback.onConnected();
                }

                // 连接陈公公，发送握手消息
                MessageProtobuf.Msg handshakeMsg = getHandshakeMsg();
                if (handshakeMsg != null) {
                    Log.d(TAG, "Send handshake message:" + handshakeMsg);
                    sendMsg(handshakeMsg, false);
                } else {
                    Log.e(TAG, "Pelase structure handshake message");
                }
                break;
            case IMSConfig.CONNECT_STATE_FAILURE:
            default:
                Log.d(TAG, "IMService connect failure");
                if (imsConnectStatusCallback != null) {
                    imsConnectStatusCallback.onConnectFailed();
                }
                break;
        }
    }

    @Override
    public void close() {
        if (isClosed) {
            return;
        }

        isClosed = true;

        try {
            // 关闭channel
            closeChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 关闭bootstrap
            if (bootstrap != null) {
                bootstrap.group().shutdownGracefully();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 释放线程池
            if (loopGroup != null) {
                loopGroup.destory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            try {
                if (serverUrlList != null) {
                    serverUrlList.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            isReconnecting = false;
            channel = null;
            bootstrap = null;
        }
    }

    @Override
    public boolean isClose() {
        return isClosed;
    }

    @Override
    public void sendMsg(MessageProtobuf.Msg msg) {
        this.sendMsg(msg, true);
    }

    @Override
    public void sendMsg(MessageProtobuf.Msg msg, boolean isJoinTimeoutManager) {
        if (msg == null || msg.getHead() == null) {
            Log.e(TAG, "Send message failure, message can't not null");
            return;
        }

        if (!TextUtils.isEmpty(msg.getHead().getMsgId())) {
            if (isJoinTimeoutManager) {
                msgTimeoutTimerManager.add(msg);
            }
        }

        if (channel == null) {
            Log.e(TAG, "Send message failure, channel can't not null");
        }

        try {
            channel.writeAndFlush(msg);
        } catch (Exception e) {
            Log.d(TAG, "Send message failure, reason:" + e.getMessage() + "\tmessage=" + msg);
        }
    }

    @Override
    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
        if (this.appStatus == IMSConfig.APP_STATUS_FOREGROUND) {
            heartbeatInterval = foregroundHeartbeatInterval;
        } else if (this.appStatus == IMSConfig.APP_STATUS_BACKGROUND) {
            heartbeatInterval = backgroundHeartbeatInterval;
        }

        addHeartbeatHandler();
    }

    private void addHeartbeatHandler() {
        if (channel == null || !channel.isActive() || channel.pipeline() == null) {
            return;
        }

        try {
            // 之前存在的读写超时handler，先移除掉，再重新添加
            if (channel.pipeline().get(IdleStateHandler.class.getSimpleName()) != null) {
                channel.pipeline().remove(IdleStateHandler.class.getSimpleName());
            }
            // 3次心跳没响应，代表连接已断开
            channel.pipeline().addFirst(IdleStateHandler.class.getSimpleName(), new IdleStateHandler(
                    heartbeatInterval * 3, heartbeatInterval, 0, TimeUnit.MILLISECONDS));

            // 重新添加HeartbeatHandler
            if (channel.pipeline().get(HeartbeatHandler.class.getSimpleName()) != null) {
                channel.pipeline().remove(HeartbeatHandler.class.getSimpleName());
            }
            if (channel.pipeline().get(TCPReadHandler.class.getSimpleName()) != null) {
                channel.pipeline().addBefore(TCPReadHandler.class.getSimpleName(), HeartbeatHandler.class.getSimpleName(),
                        new HeartbeatHandler(this));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "添加心跳消息管理handler失败，reason：" + e.getMessage());
        }
    }

    private void removeHandler(String handlerName) {
        try {
            if (channel != null && channel.pipeline().get(handlerName) != null) {
                channel.pipeline().remove(handlerName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "移除handler失败，handlerName=" + handlerName);
        }
    }


    @Override
    public MsgDispatcher getMesgDispatcher() {
        return null;
    }

    @Override
    public MsgTimeoutTimerManager getMsgTimeoutTimerManager() {
        return null;
    }

    @Override
    public int getReconnectInterval() {
        return 0;
    }

    @Override
    public int getConnectTimeout() {
        return 0;
    }

    @Override
    public int getForegroundHeartbeatInterval() {
        return 0;
    }

    @Override
    public int getBackgroundHeartbeatInterval() {
        return 0;
    }

    @Override
    public MessageProtobuf.Msg getHandshakeMsg() {
        return null;
    }

    @Override
    public MessageProtobuf.Msg getHeartbeatMsg() {
        return null;
    }

    @Override
    public int getServerSentReportMsgType() {
        return 0;
    }

    @Override
    public int getClientReceivedReportMsgType() {
        return 0;
    }

    @Override
    public int getResendCount() {
        return 0;
    }

    @Override
    public int getResendInterval() {
        return 0;
    }

    /**
     * 从连任务
     */
    private class ResetConnectRunnable implements Runnable {

        private boolean isFirst;

        public ResetConnectRunnable(boolean isFirst) {
            this.isFirst = isFirst;
        }

        @Override
        public void run() {
            // 非首次进行重连，执行到这里即代表已经连接失败，回调连接状态到应用层
            if (!isFirst) {
                onConnectStatusCallback(IMSConfig.CONNECT_STATE_FAILURE);
            }

            try {
                // 重连时，释放工作线程组，也就是停止心跳
                loopGroup.destoryWorkLoopGroup();

                while (!isClosed) {
                    if(!isNetworkAvailable()) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }

                    // 网络可用才进行连接
                    int status;
                    if ((status = reconnect()) == IMSConfig.CONNECT_STATE_SUCCESSFUL) {
                        onConnectStatusCallback(status);
                        // 连接成功，跳出循环
                        break;
                    }

                    if (status == IMSConfig.CONNECT_STATE_FAILURE) {
                        onConnectStatusCallback(status);
                        try {
                            Thread.sleep(IMSConfig.DEFAULT_RECONNECT_INTERVAL);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } finally {
                // 标识重连任务停止
                isReconnecting = false;
            }
        }

        private int reconnect() {
            // 未关闭才去连接
            if (!isClosed) {
                try {
                    // 先释放EventLoop线程组
                    if (bootstrap != null) {
                        bootstrap.group().shutdownGracefully();
                    }
                } finally {
                    bootstrap = null;
                }

                // 初始化bootstrap
                initBootstrap();
                return connectServer();
            }
            return IMSConfig.CONNECT_STATE_FAILURE;
        }

        private int connectServer() {
            return 0;
        }
    }

    private void initBootstrap() {
        EventLoopGroup loopGroup = new NioEventLoopGroup(4);
        bootstrap = new Bootstrap();
        bootstrap.group(loopGroup).channel(NioSocketChannel.class);
        // 设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        // 设置禁用nagle算法
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        // 设置连接超时时长
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, getConnectTimeout());
        // 设置初始化Channel
        bootstrap.handler(new TCPChannelInitializerHandler(this));
    }


    private boolean isNetworkAvailable() {
        return false;
    }
}
