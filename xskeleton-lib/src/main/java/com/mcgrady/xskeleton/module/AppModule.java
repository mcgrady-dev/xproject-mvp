package com.mcgrady.xskeleton.module;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mcgrady.xskeleton.cache.Cache;
import com.mcgrady.xskeleton.cache.CacheType;
import com.mcgrady.xskeleton.http.imageloader.BaseImageLoaderStrategy;
import com.mcgrady.xskeleton.http.imageloader.ImageLoader;
import com.mcgrady.xskeleton.integration.IRepositoryManager;

import java.io.File;

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
    private File cacheFile;
    private Cache.Factory cacheFactory;
    private ImageLoader imageLoader;

    public ImageLoader imageLoader() {
        return imageLoader;
    }

    public Cache.Factory cacheFactory() {
        return cacheFactory;
    }

    public Gson gson() {
        return gson;
    }

    public IRepositoryManager repositoryManager() {
        return repositoryManager;
    }

    public Cache<String, Object> extras() {
        return extras;
    }

    public File cacheFile() {
        return cacheFile;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Application application;
        private GsonConfiguration gsonConfig;
        private Cache.Factory cacheFactory;
        private File cacheFile;
        private BaseImageLoaderStrategy imageLoaderStrategy;

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

        public Builder imageLoaderStrategy(BaseImageLoaderStrategy loaderStrategy) {
            this.imageLoaderStrategy = loaderStrategy;
            return this;
        }

        public Builder cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }

        public AppModule build() {
            AppModule appModule = new AppModule();

            GsonBuilder builder = new GsonBuilder();
            if (gsonConfig != null) {
                gsonConfig.configGson(application, builder);
            }
            appModule.gson = builder.create();

            appModule.cacheFile = cacheFile;
            appModule.cacheFactory = cacheFactory;
            if (cacheFactory != null) {
                appModule.extras = cacheFactory.build(CacheType.EXTRAS);
            }

            appModule.imageLoader = new ImageLoader(imageLoaderStrategy);

            return appModule;
        }
    }

    public interface GsonConfiguration {
        void configGson(@NonNull Context context, @NonNull GsonBuilder builder);
    }
}
