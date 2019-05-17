package com.mcgrady.ximlib;

import com.mcgrady.ximlib.interf.IMSClientInterface;
import com.mcgrady.ximlib.proto.MessageProtobuf;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息发送超时管理器，用于管理消息定时器的新增、移除等
 *
 * Created by mcgrady on 2019/5/17.
 */
public class MsgTimeoutTimerManager {

    private Map<String, MsgTimeoutTimer> msgTimeoutMap = new ConcurrentHashMap<>();
    private IMSClientInterface imsClient;

    public MsgTimeoutTimerManager(IMSClientInterface imsClient) {
        this.imsClient = imsClient;
    }


    public void add(MessageProtobuf.Msg msg) {

    }
}
