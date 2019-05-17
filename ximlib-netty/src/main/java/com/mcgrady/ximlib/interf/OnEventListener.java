package com.mcgrady.ximlib.interf;

import com.mcgrady.ximlib.proto.MessageProtobuf;

/**
 * Created by mcgrady on 2019/5/17.
 */
public interface OnEventListener {

    /**
     * 分发消息到应用层
     *
     * @param msg
     */
    void dispathMsg(MessageProtobuf.Msg msg);

    /**
     * 网络是否可用
     *
     * @return
     */
    boolean isNetworkAvailable();

    /**
     * 重连间隔时长
     *
     * @return
     */
    int getReconnectInterval();

    /**
     * 连接超时时长
     *
     * @return
     */
    int getConnectTimeout();

    /**
     * 在前台时心跳间隔时长
     *
     * @return
     */
    int getForegroundHeartbeatInterval();

    /**
     * 在后台时心跳间隔时长
     *
     * @return
     */
    int getBackgroundHeartbeatInterval();

    /**
     * 握手消息
     *
     * @return
     */
    MessageProtobuf.Msg getHandshakeMsg();

    /**
     * 心跳消息
     *
     * @return
     */
    MessageProtobuf.Msg getHeartbeatMsg();

    /**
     * 消息发送类型
     *
     * @return
     */
    int getServerSentReportMsgType();

    /**
     * 消息接收类型
     *
     * @return
     */
    int getClientReceivedReportMsgType();

    /**
     * 消息发送超时重发次数
     *
     * @return
     */
    int getResendCount();

    /**
     * 消息发送超时重发间隔
     *
     * @return
     */
    int getResendInterval();
}
