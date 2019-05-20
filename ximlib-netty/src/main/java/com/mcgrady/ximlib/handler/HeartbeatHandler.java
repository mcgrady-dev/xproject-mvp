package com.mcgrady.ximlib.handler;

import android.util.Log;

import com.mcgrady.ximlib.netty.NettyTcpClient;
import com.mcgrady.ximlib.proto.MessageProtobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 心跳任务管理器
 *
 * Created by mcgrady on 2019/5/17.
 */
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
    private final String TAG = HeartbeatHandler.class.getSimpleName();

    private NettyTcpClient imsClient;

    public HeartbeatHandler(NettyTcpClient imsClient) {
        this.imsClient = imsClient;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleState idleState = ((IdleStateEvent) evt).state();
            switch (idleState) {
                case READER_IDLE:
                    //规定时间内没收到服务端心跳包响应时，进行重连操作
                    imsClient.resetConnect(false);
                    break;
                case WRITER_IDLE:
                    //向服务端发送心跳包
                    if (heartbeatTask == null) {
                        heartbeatTask = new HeartbeatTask(ctx);
                    }

                    imsClient.getLoopGroup().execWorkTask(heartbeatTask);
                    break;
                default:
                    break;
            }
        }
    }

    private HeartbeatTask heartbeatTask;

    private class HeartbeatTask implements Runnable {

        private ChannelHandlerContext context;

        public HeartbeatTask(ChannelHandlerContext context) {
            this.context = context;
        }

        @Override
        public void run() {
            if (context.channel().isActive()) {
                MessageProtobuf.Msg heartbeatMsg = imsClient.getHeartbeatMsg();
                if (heartbeatMsg == null) {
                    return;
                }

                Log.d(TAG, "发送心跳消息，message=" + heartbeatMsg + "当前心跳间隔为：" + imsClient.getHeartbeatInterval() + "ms\n");
                imsClient.sendMsg(heartbeatMsg, false);
            }
        }
    }
}
