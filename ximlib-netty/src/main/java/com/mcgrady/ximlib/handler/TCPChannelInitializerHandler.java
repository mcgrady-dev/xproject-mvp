package com.mcgrady.ximlib.handler;

import com.mcgrady.ximlib.netty.NettyTcpClient;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by mcgrady on 2019/5/17.
 */
public class TCPChannelInitializerHandler implements ChannelHandler {

    private NettyTcpClient imsClient;

    public TCPChannelInitializerHandler(NettyTcpClient imsClient) {
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {

    }
}
