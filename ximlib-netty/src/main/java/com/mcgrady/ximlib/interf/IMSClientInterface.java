package com.mcgrady.ximlib.interf;

import com.mcgrady.ximlib.MsgDispatcher;
import com.mcgrady.ximlib.MsgTimeoutTimerManager;
import com.mcgrady.ximlib.proto.MessageProtobuf;

import java.util.Vector;

/**
 * <p>ims抽象接口，需要切换到其它方式实现im功能，实现此接口即可</p>
 * Created by mcgrady on 2019/5/17.
 */
public interface IMSClientInterface {

    /**
     * 初始化
     *
     * @param serverUrlList 服务器地址列表
     * @param listener 与应用层交互的Listener
     * @param callback ims连接状态回调
     */
    void init(Vector<String> serverUrlList, OnEventListener listener, IMSConnectStatusCallback callback);

    /**
     * 重置连接（重连）
     *
     */
    void resetConnect();

    /**
     * 重置连接（重连）
     *
     * @param isFirst 是否首次连接，首次连接也可认为时重连
     */
    void resetConnect(boolean isFirst);

    /**
     * 关闭连接，同时释放资源
     */
    void close();

    /**
     * 标识ims是否关闭
     *
     * @return
     */
    boolean isClose();

    /**
     * 发送消息
     *
     * @param msg
     */
    void sendMsg(MessageProtobuf.Msg msg);

    /**
     * 发送消息，重载
     *
     * @param msg
     * @param isJoinTimeoutManager 是否加入发送超时管理器
     */
    void sendMsg(MessageProtobuf.Msg msg, boolean isJoinTimeoutManager);

    /**
     * 设置app前台状态
     *
     * @param appStatus
     */
    void setAppStatus(int appStatus);

    /**
     * 消息转发器
     *
     * @return
     */
    MsgDispatcher getMesgDispatcher();

    /**
     * 消息发送超时定时器
     *
     * @return
     */
    MsgTimeoutTimerManager getMsgTimeoutTimerManager();

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
