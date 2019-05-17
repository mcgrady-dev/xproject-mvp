package com.mcgrady.ximlib.handler;

import com.mcgrady.ximlib.netty.NettyTcpClient;

import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by mcgrady on 2019/5/17.
 */
public class TCPReadHandler extends ChannelInboundHandlerAdapter {

    private NettyTcpClient imsClient;

    public TCPReadHandler(NettyTcpClient imsClient) {
        this.imsClient = imsClient;
    }
}
