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

    private boolean isClosed = false;// 标识ims是否已关闭
    private Vector<String> serverUrlList;// ims服务器地址组
    private OnEventListener onEventListener;// 与应用层交互的listener
    private IMSConnectStatusCallback imsConnectStatusCallback;// ims连接状态回调监听器
    private MsgDispatcher msgDispatcher;// 消息转发器
    private ExecutorServiceFactory loopGroup;// 线程池工厂

    private boolean isReconnecting = false;// 是否正在进行重连
    private int connectStatus = IMSConfig.CONNECT_STATE_FAILURE;// ims连接状态，初始化为连接失败
    // 重连间隔时长
    private int reconnectInterval = IMSConfig.DEFAULT_RECONNECT_BASE_DELAY_TIME;
    // 连接超时时长
    private int connectTimeout = IMSConfig.DEFAULT_CONNECT_TIMEOUT;
    // 心跳间隔时间
    private int heartbeatInterval = IMSConfig.DEFAULT_HEARTBEAT_INTERVAL_FOREGROUND;
    // 应用在后台时心跳间隔时间
    private int foregroundHeartbeatInterval = IMSConfig.DEFAULT_HEARTBEAT_INTERVAL_FOREGROUND;
    // 应用在前台时心跳间隔时间
    private int backgroundHeartbeatInterval = IMSConfig.DEFAULT_HEARTBEAT_INTERVAL_BACKGROUND;
    // app前后台状态
    private int appStatus = IMSConfig.APP_STATUS_FOREGROUND;
    // 消息发送超时重发次数
    private int resendCount = IMSConfig.DEFAULT_RESEND_COUNT;
    // 消息发送失败重发间隔时长
    private int resendInterval = IMSConfig.DEFAULT_RESEND_INTERVAL;

    private String currentHost = null;// 当前连接host
    private int currentPort = -1;// 当前连接port

    private MsgTimeoutTimerManager msgTimeoutTimerManager;// 消息发送超时定时器管理


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
        msgDispatcher = new MsgDispatcher();
        msgDispatcher.setOnEventListener(listener);
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

    /**
     * 关闭channel
     */
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

    /**
     * 回调ims连接状态
     *
     * @param connectStatus
     */
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
    public boolean isClosed() {
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

    /**
     * 添加心跳信息管理handler
     */
    public void addHeartbeatHandler() {
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

    /**
     * 移除指定handler
     * @param handlerName
     */
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
    public MsgDispatcher getMsgDispatcher() {
        return msgDispatcher;
    }

    @Override
    public MsgTimeoutTimerManager getMsgTimeoutTimerManager() {
        return msgTimeoutTimerManager;
    }

    @Override
    public int getReconnectInterval() {
        if (onEventListener != null && onEventListener.getReconnectInterval() > 0) {
            return reconnectInterval = onEventListener.getReconnectInterval();
        }
        return reconnectInterval;
    }

    @Override
    public int getConnectTimeout() {
        if (onEventListener != null && onEventListener.getConnectTimeout() > 0) {
            return connectTimeout = onEventListener.getConnectTimeout();
        }
        return connectTimeout;
    }

    @Override
    public int getForegroundHeartbeatInterval() {
        if (onEventListener != null && onEventListener.getForegroundHeartbeatInterval() > 0) {
            foregroundHeartbeatInterval = onEventListener.getForegroundHeartbeatInterval();
        }
        return foregroundHeartbeatInterval;
    }

    @Override
    public int getBackgroundHeartbeatInterval() {
        if (onEventListener != null && onEventListener.getBackgroundHeartbeatInterval() > 0) {
            return backgroundHeartbeatInterval = onEventListener.getBackgroundHeartbeatInterval();
        }
        return backgroundHeartbeatInterval;
    }

    @Override
    public MessageProtobuf.Msg getHandshakeMsg() {
        if (onEventListener != null) {
            return onEventListener.getHandshakeMsg();
        }
        return null;
    }

    @Override
    public MessageProtobuf.Msg getHeartbeatMsg() {
        if (onEventListener != null) {
            onEventListener.getHeartbeatMsg();
        }
        return null;
    }

    @Override
    public int getServerSentReportMsgType() {
        if (onEventListener != null) {
            onEventListener.getServerSentReportMsgType();
        }
        return 0;
    }

    @Override
    public int getClientReceivedReportMsgType() {
        if (onEventListener != null) {
            onEventListener.getClientReceivedReportMsgType();
        }
        return 0;
    }

    @Override
    public int getResendCount() {
        if (onEventListener != null && onEventListener.getResendCount() != 0) {
            return resendCount = onEventListener.getResendCount();
        }
        return resendCount;
    }

    @Override
    public int getResendInterval() {
        if (onEventListener != null && onEventListener.getResendInterval() > 0) {
            return resendInterval = onEventListener.getResendInterval();
        }
        return resendInterval;
    }


    /**
     * 初始化bootstrap
     */
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

    /**
     * 从应用层获取网络是否可用
     * @return
     */
    private boolean isNetworkAvailable() {
        if (onEventListener != null) {
            return onEventListener.isNetworkAvailable();
        }
        return false;
    }

    /**
     * 连接服务器
     */
    private void toServer() {
        try {
            channel = bootstrap.connect(currentHost, currentPort).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e(TAG, String.format("连接Server(ip[%s], port[%s])失败", currentHost, currentPort));
    }

    public int getHeartbeatInterval() {
        return this.heartbeatInterval;
    }

    public ExecutorServiceFactory getLoopGroup() {
        return loopGroup;
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

        /**
         * 重连，首次连接也认为是第一次重连
         *
         * @return
         */
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

        /**
         * 连接服务器
         *
         * @return
         */
        private int connectServer() {
            //如果服务器地址无效，直接回调连接状态，不再进行连接
            if (serverUrlList == null || serverUrlList.size() <= 0) {
                return IMSConfig.CONNECT_STATE_FAILURE;
            }

            for (int i = 0; (!isClosed && i < serverUrlList.size()); i++) {
                String serverUrl = serverUrlList.get(i);
                if (TextUtils.isEmpty(serverUrl)) {
                    return IMSConfig.CONNECT_STATE_FAILURE;
                }

                String[] address = serverUrl.split(" ");
                for (int j = 0; j < IMSConfig.DEFAULT_RECONNECT_COUNT; j++) {
                    //如果ims已关闭或网络不可用，直接回调连接状态，不再进行连接
                    if (isClosed || !isNetworkAvailable()) {
                        return IMSConfig.CONNECT_STATE_FAILURE;
                    }

                    //回调连接状态
                    if (connectStatus != IMSConfig.CONNECT_STATE_CONNECTING) {
                        onConnectStatusCallback(IMSConfig.CONNECT_STATE_CONNECTING);
                    }

                    Log.d(TAG, String.format("正在进行『%s』的第『%d』次连接，当前重连延时时长为『%dms』", serverUrl, j, j * getReconnectInterval()));

                    try {
                        currentHost = address[0];
                        currentPort = Integer.parseInt(address[i]);
                        toServer();

                        if (channel != null) {
                            return IMSConfig.CONNECT_STATE_SUCCESSFUL;
                        } else {
                            //连接失败，线程休眠，等待重连
                            Thread.sleep(j * getReconnectInterval());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        close();
                        break;  //线程被中断，强制退出
                    }
                }

            }

            return IMSConfig.CONNECT_STATE_FAILURE;
        }
    }

}
