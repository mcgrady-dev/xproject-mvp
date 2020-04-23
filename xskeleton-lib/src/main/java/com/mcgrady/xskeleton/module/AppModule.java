package com.mcgrady.xskeleton.module;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mcgrady.xskeleton.cache.Cache;
import com.mcgrady.xskeleton.cache.CacheType;
import com.mcgrady.xskeleton.integration.IRepositoryManager;

/**
 * Created by mcgrady on 2020/4/3.
 */
public class AppModule {

//    private FragmentManager.FragmentLifecycleCallbacks fragmentLiftcycle;
//    private Application.ActivityLifecycleCallbacks activityLifecycle;
//    private Application.ActivityLifecycleCallbacks activityRxLifecycle;
    private Gson gson;
    private IRepositoryManager repositoryManager;
    private Cache<String, Object> extras;
    private Cache.Factory cacheFactory;


    public Cache.Factory getCacheFactory() {
        return cacheFactory;
    }

    public Gson getGson() {
        return gson;
    }

    public IRepositoryManager getRepositoryManager() {
        return repositoryManager;
    }

    public Cache<String, Object> getExtras() {
        return extras;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Application application;
        private GsonConfiguration gsonConfig;
        private Cache.Factory cacheFactory;

        private Builder() {
        }

        public Builder with(Application application) {
            this.application = application;
            return this;
        }

        public Builder gsonConfig(GsonConfiguration gsonConfig) {
            this.gsonConfig = gsonConfig;
            return this;
        }

        public Builder cacheFactory(@NonNull Cache.Factory cacheFactory) {
            this.cacheFactory = cacheFactory;
            return this;
        }

        public AppModule build() {
            AppModule appModule = new AppModule();

            GsonBuilder builder = new GsonBuilder();
            if (gsonConfig != null) {
                gsonConfig.configGson(application, builder);
            }
            appModule.gson = builder.create();

            appModule.cacheFactory = cacheFactory;
            if (cacheFactory != null) {
                appModule.extras = cacheFactory.build(CacheType.EXTRAS);
            }

            return appModule;
        }
    }

    public interface GsonConfiguration {
        void configGson(@NonNull Context context, @NonNull GsonBuilder builder);
    }
}
