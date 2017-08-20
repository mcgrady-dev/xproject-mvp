package com.mcgrady.xproject.di.module;

import com.mcgrady.xproject.app.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mcgrady on 2017/7/27.
 */

@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }
}
