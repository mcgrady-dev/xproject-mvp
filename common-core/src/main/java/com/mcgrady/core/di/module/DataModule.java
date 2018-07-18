package com.mcgrady.core.di.module;

import com.mcgrady.core.base.BaseApplication;
import com.mcgrady.core.http.HttpHelper;
import com.mcgrady.core.http.IHttpHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * <p></p>
 * @author: mcgrady
 * @date: 2018/5/9
 */

@Module
public class DataModule {

    IHttpHelper.NetConfig netConfig;

    public DataModule(IHttpHelper.NetConfig netConfig) {
        this.netConfig = netConfig;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(BaseApplication application) {
        HttpHelper httpHelper = new HttpHelper(application);
        if (netConfig == null) {
            netConfig = new IHttpHelper.NetConfig();
        }
        httpHelper.initConfig(netConfig);
        return httpHelper;
    }
}
