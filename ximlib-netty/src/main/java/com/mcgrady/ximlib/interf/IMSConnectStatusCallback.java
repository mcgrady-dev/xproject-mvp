package com.mcgrady.ximlib.interf;

/**
 * IMS连接状态回调
 *
 * Created by mcgrady on 2019/5/17.
 */
public interface IMSConnectStatusCallback {

    /**
     * 连接中
     */
    void onConnecting();

    /**
     * 连接成功
     */
    void onConnected();

    /**
     * 连接失败
     */
    void onConnectFailed();
}
