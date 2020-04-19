package com.mcgrady.xskeleton.base;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.mcgrady.xskeleton.cache.CacheType;
import com.mcgrady.xskeleton.cache.LruCache;
import com.mcgrady.xskeleton.integration.ConfigModule;
import com.mcgrady.xskeleton.integration.ManifestParser;
import com.mcgrady.xskeleton.integration.cache.IntelligentCache;
import com.mcgrady.xskeleton.module.AppModule;
import com.mcgrady.xskeleton.module.ClientModule;
import com.mcgrady.xskeleton.module.GlobalConfigModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcgrady on 2020/4/3.
 */
public class AppDelegate implements AppLifecycles, IApp {

    protected Application.ActivityLifecycleCallbacks activityLifecycle;
    protected Application.ActivityLifecycleCallbacks activityForRxLifecycle;

    private Application application;
    private List<ConfigModule> modules;
    private List<AppLifecycles> appLifecycles = new ArrayList<>();
    private List<Application.ActivityLifecycleCallbacks> activityLifecycles = new ArrayList<>();
    private ComponentCallbacks2 componentCallback;

    private GlobalConfigModule globalConfigModule;
    private AppModule appModule;
    private ClientModule clientModule;

    public AppDelegate(@NonNull Context context) {
        this.modules = new ManifestParser(context).parse();

        for (ConfigModule module : modules) {
            module.injectAppLifecycle(context, appLifecycles);
            module.injectActivityLifecycle(context, activityLifecycles);
        }
    }

    @Override
    public void attachBaseContext(@NonNull Context base) {
        for (AppLifecycles appLifecycle : appLifecycles) {
            appLifecycle.attachBaseContext(base);
        }
    }

    @Override
    public void onCreate(@NonNull Application application) {
        this.application = application;

        globalConfigModule = getGlobalConfigModule(application, modules);

        appModule = AppModule.builder()
                .with(application)
                .gsonConfig(globalConfigModule.getGsonConfig())
                .cacheFactory(type -> {
                    //若想自定义 LruCache 的 size, 或者不想使用 LruCache, 想使用自己自定义的策略
                    //使用 GlobalConfigModule.Builder#cacheFactory() 即可扩展
                    switch (type.getCacheTypeId()) {
                        //Activity、Fragment 以及 Extras 使用 IntelligentCache (具有 LruCache 和 可永久存储数据的 Map)
                        case CacheType.EXTRAS_TYPE_ID:
                        case CacheType.ACTIVITY_CACHE_TYPE_ID:
                        case CacheType.FRAGMENT_CACHE_TYPE_ID:
                            return new IntelligentCache(type.calculateCacheSize(application));
                        //其余使用 LruCache (当达到最大容量时可根据 LRU 算法抛弃不合规数据)
                        default:
                            return new LruCache(type.calculateCacheSize(application));
                    }
                })
                .build();

        clientModule = ClientModule.builder()
                .with(application)
                .httpUrl(globalConfigModule.getApiUrl())
                .gson(appModule.getGson())
                .retrofitConfig(globalConfigModule.getRetrofitConfig())
                .okhttpClientConfig(globalConfigModule.getOkhttpConfig())
                .interceptors(globalConfigModule.getInterceptors())
                .globalHttpHandler(globalConfigModule.getHttpHandler())
                .executorService(globalConfigModule.getExecutorService())
                .build();

        appModule.getExtras().put(IntelligentCache.getKeyOfKeep(ConfigModule.class.getName()), modules);

        this.modules = null;

        application.registerActivityLifecycleCallbacks(activityLifecycle);
        application.registerActivityLifecycleCallbacks(activityForRxLifecycle);
        for (Application.ActivityLifecycleCallbacks activityLifecycle : activityLifecycles) {
            application.registerActivityLifecycleCallbacks(activityLifecycle);
        }

        componentCallback = new AppComponentCallbacks();
        application.registerComponentCallbacks(componentCallback);

        for (AppLifecycles appLifecycle : appLifecycles) {
            appLifecycle.onCreate(application);
        }
    }

    @Override
    public void onTerminate(@NonNull Application application) {
        if (activityLifecycle != null) {
            this.application.unregisterActivityLifecycleCallbacks(activityLifecycle);
        }
        if (activityForRxLifecycle != null) {
            this.application.unregisterActivityLifecycleCallbacks(activityForRxLifecycle);
        }
        if (componentCallback != null) {
            this.application.unregisterComponentCallbacks(componentCallback);
        }
        if (activityLifecycles != null && !activityLifecycles.isEmpty()) {
            for (Application.ActivityLifecycleCallbacks activityLifecycle : activityLifecycles) {
                this.application.unregisterActivityLifecycleCallbacks(activityLifecycle);
            }
        }
        if (appLifecycles != null && !appLifecycles.isEmpty()) {
            for (AppLifecycles appLifecycle : appLifecycles) {
                appLifecycle.onTerminate(this.application);
            }
        }

        this.activityLifecycle = null;
        this.activityForRxLifecycle = null;
        this.activityLifecycles = null;
        this.componentCallback = null;
        this.appLifecycles = null;
        this.application = null;
    }

    private GlobalConfigModule getGlobalConfigModule(Context context, List<ConfigModule> modules) {
        GlobalConfigModule.Builder builder = GlobalConfigModule.builder();

        for (ConfigModule configModule : modules) {
            configModule.applyOptions(context, builder);
        }

        return builder.build();
    }

    @NonNull
    @Override
    public ClientModule getClientModule() {
        return clientModule;
    }

    @NonNull
    @Override
    public AppModule getAppModule() {
        return appModule;
    }

    private static class AppComponentCallbacks implements ComponentCallbacks2 {

        @Override
        public void onTrimMemory(int level) {

        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {

        }

        @Override
        public void onLowMemory() {

        }
    }
}
