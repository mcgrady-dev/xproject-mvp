package com.mcgrady.core.di.module;

import com.mcgrady.core.base.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * <p></p>
 * @author: mcgrady
 * @date: 2018/5/9
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
