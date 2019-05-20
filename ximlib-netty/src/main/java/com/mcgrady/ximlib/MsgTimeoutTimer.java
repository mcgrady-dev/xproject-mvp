package com.mcgrady.ximlib;

import android.util.Log;

import com.mcgrady.ximlib.interf.IMSClientInterface;
import com.mcgrady.ximlib.proto.MessageProtobuf;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 消息发送超时定时器，每一条消息对应一个定时器
 *
 * Created by mcgrady on 2019/5/17.
 */
class MsgTimeoutTimer extends Timer {

    private final String TAG = MsgTimeoutTimer.class.getSimpleName();

    private IMSClientInterface imsClient;// ims客户端
    private MessageProtobuf.Msg msg;// 发送的消息
    private int currentResendCount;// 当前重发次数
    private MsgTimeoutTask task;// 消息发送超时任务

    public MsgTimeoutTimer(IMSClientInterface imsClient, MessageProtobuf.Msg msg) {
        this.imsClient = imsClient;
        this.msg = msg;
        task = new MsgTimeoutTask();
        this.schedule(task, imsClient.getResendInterval(), imsClient.getResendInterval());
    }

    /**
     * 消息发送超时任务
     */
    private class MsgTimeoutTask extends TimerTask {

        @Override
        public void run() {
            if (imsClient.isClosed()) {
                if (imsClient.getMsgTimeoutTimerManager() != null) {
                    imsClient.getMsgTimeoutTimerManager().remove(msg.getHead().getMsgId());
                }

                return;
            }

            currentResendCount++;
            if (currentResendCount > imsClient.getResendCount()) {
                // 重发次数大于可重发次数，直接标识为发送失败，并通过消息转发器通知应用层
                try {
                    MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
                    MessageProtobuf.Head.Builder headBuilder = MessageProtobuf.Head.newBuilder();
                    headBuilder.setMsgId(msg.getHead().getMsgId());
                    headBuilder.setMsgType(imsClient.getServerSentReportMsgType());
                    headBuilder.setTimestamp(System.currentTimeMillis());
                    headBuilder.setStatusReport(IMSConfig.DEFAULT_REPORT_SERVER_SEND_MSG_FAILURE);
                    builder.setHead(headBuilder.build());

                    // 通知应用层消息发送失败
                    imsClient.getMsgDispatcher().receivedMsg(builder.build());
                } finally {
                    // 从消息发送超时管理器移除该消息
                    imsClient.getMsgTimeoutTimerManager().remove(msg.getHead().getMsgId());
                    // 执行到这里，认为连接已断开或不稳定，触发重连
                    imsClient.resetConnect();
                    currentResendCount = 0;
                }
            } else {
                // 发送消息，但不再加入超时管理器，达到最大发送失败次数就算了
                sendMsg();
            }
        }
    }

    public void sendMsg() {
        Log.d(TAG, "正在重发消息，message=" + msg);
        imsClient.sendMsg(msg, false);
    }

    public MessageProtobuf.Msg getMsg() {
        return msg;
    }

    @Override
    public void cancel() {
        if (task != null) {
            task.cancel();
            task = null;
        }

        super.cancel();
    }
}
