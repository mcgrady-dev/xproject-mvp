package com.mcgrady.ximlib;

import com.mcgrady.ximlib.interf.OnEventListener;
import com.mcgrady.ximlib.proto.MessageProtobuf;

/**
 * Created by mcgrady on 2019/5/17.
 */
public class MsgDispatcher {

    private OnEventListener onEventListener;

    public MsgDispatcher() {

    }

    public void setOnEventListener(OnEventListener listener) {
        this.onEventListener = listener;
    }

    public void receivedMsg(MessageProtobuf.Msg msg) {
        if (onEventListener != null) {
            onEventListener.dispathMsg(msg);
        }
    }
}
