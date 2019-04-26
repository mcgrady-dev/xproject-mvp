package com.mcgrady.xskeleton.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mcgrady.xskeleton.integration.IRepositoryManager;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by mcgrady on 2019/4/26.
 */
@Module
public abstract class AppModule {

    @Singleton
    @Provides
    static Gson provideGson(Application application, GsonConfig gsonConfig) {
        GsonBuilder builder = new GsonBuilder();
        if (gsonConfig != null) {
            gsonConfig.configGson(application, builder);
        }

        return builder.create();
    }


    @Binds
    abstract IRepositoryManager bindRepositoryManager(IRepositoryManager repositoryManager);




    public interface GsonConfig {
        void configGson(@NonNull Context context, @NonNull GsonBuilder builder);
    }
}
