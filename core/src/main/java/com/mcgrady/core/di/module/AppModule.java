package com.mcgrady.core.di.module;

import com.mcgrady.core.base.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/24
 * @des:
 */
@Module
public class AppModule {
    private final BaseApplication mApplication;

    public AppModule(BaseApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    BaseApplication provideApplicationContext() {
        return mApplication;
    }
}
