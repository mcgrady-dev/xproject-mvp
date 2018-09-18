package com.mcgrady.test;

import com.mcgrady.core.base.BaseApplication;
import com.mcgrady.core.http.IHttpHelper;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/9/17
 */

public class TestApp extends BaseApplication {

    @Override
    public IHttpHelper.NetConfig getNetConfig() {
        return new IHttpHelper.NetConfig().configBaseURL("");
    }
}
