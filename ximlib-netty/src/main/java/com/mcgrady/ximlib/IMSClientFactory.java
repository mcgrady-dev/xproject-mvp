package com.mcgrady.ximlib;

import com.mcgrady.ximlib.interf.IMSClientInterface;
import com.mcgrady.ximlib.netty.NettyTcpClient;

/**
 * ims实例工厂方法
 *
 * Created by mcgrady on 2019/5/20.
 */
public class IMSClientFactory {

    public static IMSClientInterface getIMSClient() {
        return NettyTcpClient.getInstance();
    }
}
