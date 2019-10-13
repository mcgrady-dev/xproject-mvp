package com.mcgrady.xskeleton.di.module;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mcgrady.xskeleton.cache.Cache;
import com.mcgrady.xskeleton.cache.CacheType;
import com.mcgrady.xskeleton.integration.IRepositoryManager;
import com.mcgrady.xskeleton.integration.RepositoryManager;
import com.mcgrady.xskeleton.lifecycle.ActivityLifecycle;
import com.mcgrady.xskeleton.lifecycle.ActivityRxLifecycle;
import com.mcgrady.xskeleton.lifecycle.FragmentLifecycle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * 提供一些框架必须的实例
 *
 * Created by mcgrady on 2019/4/26.
 */
@Module
public abstract class AppModule {

    @Singleton
    @Provides
    static Gson provideGson(Application application, @Nullable GsonConfig config) {
        GsonBuilder builder = new GsonBuilder();
        if (config != null)
            config.configGson(application, builder);
        return builder.create();
    }

    @Binds
    abstract IRepositoryManager bindRepositoryManager(RepositoryManager repositoryManager);

    @Singleton
    @Provides
    static Cache<String, Object> provideExtras(Cache.Factory cacheFactory) {
        return cacheFactory.build(CacheType.EXTRAS);
    }

    @Binds
    @Named("ActivityLifecycle")
    abstract Application.ActivityLifecycleCallbacks bindActivityLifecycle(ActivityLifecycle activityLifecycle);

    @Binds
    @Named("ActivityRxLifecycle")
    abstract Application.ActivityLifecycleCallbacks bindActivityRxLifecycle(ActivityRxLifecycle activityRxLifecycle);

    @Binds
    abstract FragmentManager.FragmentLifecycleCallbacks bindFragmentLifecycle(FragmentLifecycle fragmentLifecycle);

    @Singleton
    @Provides
    static List<FragmentManager.FragmentLifecycleCallbacks> provideFragmentLifecycles() {
        return new ArrayList<>();
    }

    public interface GsonConfig {
        void configGson(@NonNull Context context, @NonNull GsonBuilder builder);
    }
}
