package com.mcgrady.ximlib;

import android.text.TextUtils;
import android.util.Log;

import com.mcgrady.ximlib.interf.IMSClientInterface;
import com.mcgrady.ximlib.proto.MessageProtobuf;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息发送超时管理器，用于管理消息定时器的新增、移除等
 *
 * Created by mcgrady on 2019/5/17.
 */
public class MsgTimeoutTimerManager {
    private final String TAG = MsgTimeoutTimerManager.class.getSimpleName();

    private Map<String, MsgTimeoutTimer> msgTimeoutMap = new ConcurrentHashMap<>();
    private IMSClientInterface imsClient;

    public MsgTimeoutTimerManager(IMSClientInterface imsClient) {
        this.imsClient = imsClient;
    }

    /**
     * 添加消息到发送超时管理器
     * 
     * @param msg
     */
    public void add(MessageProtobuf.Msg msg) {
        if (msg == null || msg.getHead() == null) {
            return;
        }

        int handshakeMsgType = -1;
        int heartbeatMsgType = -1;
        int clientReceivedReportMsgType = imsClient.getClientReceivedReportMsgType();
        MessageProtobuf.Msg handshakeMsg = imsClient.getHandshakeMsg();
        if (handshakeMsg != null && handshakeMsg.getHead() != null) {
            handshakeMsgType = handshakeMsg.getHead().getMsgType();
        }
        MessageProtobuf.Msg heartbeatMsg = imsClient.getHeartbeatMsg();
        if (heartbeatMsg != null && heartbeatMsg.getHead() != null) {
            heartbeatMsgType = heartbeatMsg.getHead().getMsgType();
        }

        int msgType = msg.getHead().getMsgType();
        // 握手消息、心跳消息、客户端返回的状态报告消息，不用重发。
        if (msgType == handshakeMsgType || msgType == heartbeatMsgType || msgType == clientReceivedReportMsgType) {
            return;
        }

        String msgId = msg.getHead().getMsgId();
        if (!msgTimeoutMap.containsKey(msgId)) {
            MsgTimeoutTimer timer = new MsgTimeoutTimer(imsClient, msg);
            msgTimeoutMap.put(msgId, timer);
        }

        Log.d(TAG,"添加消息超发送超时管理器，message=" + msg + "\t当前管理器消息数：" + msgTimeoutMap.size());
    }

    /**
     * 从发送超时管理器中移除消息，并停止定时器
     *
     * @param msgId
     */
    public void remove(String msgId) {
        if (TextUtils.isEmpty(msgId)) {
            return;
        }

        MsgTimeoutTimer timer = msgTimeoutMap.remove(msgId);
        MessageProtobuf.Msg msg = null;
        if (timer != null) {
            msg = timer.getMsg();
            timer.cancel();
            timer = null;
        }

        Log.d(TAG, "从发送消息管理器移除消息，message=" + msg);
    }

    /**
     * 重连成功回调，重连并握手成功时，重发消息发送超时管理器中所有的消息
     */
    public synchronized void onResetConnected() {

        Set<Map.Entry<String, MsgTimeoutTimer>> entrySet =  msgTimeoutMap.entrySet();
        Iterator<Map.Entry<String, MsgTimeoutTimer>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            iterator.next().getValue().sendMsg();
        }

    }
}
