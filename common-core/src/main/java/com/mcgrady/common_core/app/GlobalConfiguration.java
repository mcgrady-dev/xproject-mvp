package com.mcgrady.common_core.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.google.gson.GsonBuilder;
import com.mcgrady.common_core.BuildConfig;
import com.mcgrady.common_core.http.Api;
import com.mcgrady.common_core.http.GlobalHttHandlerImpl;
import com.mcgrady.common_core.http.ResponseErrorListenerImpl;
import com.mcgrady.common_core.http.SSLSocketClient;
import com.mcgrady.common_core.http.manager.RetrofitUrlManager;
import com.mcgrady.common_core.imageEngine.strategy.CommonGlideImageLoaderStrategy;
import com.mcgrady.common_core.lifecycle.ActivityLifecycleCallbacksImpl;
import com.mcgrady.common_core.lifecycle.AppLifecyclesImpl;
import com.mcgrady.common_core.lifecycle.FragmentLifecycleCallbacksImpl;
import com.mcgrady.xskeleton.base.AppLifecycles;
import com.mcgrady.xskeleton.cache.CacheType;
import com.mcgrady.xskeleton.cache.LruCache;
import com.mcgrady.xskeleton.http.log.RequestInterceptor;
import com.mcgrady.xskeleton.integration.ConfigModule;
import com.mcgrady.xskeleton.integration.cache.IntelligentCache;
import com.mcgrady.xskeleton.module.AppModule;
import com.mcgrady.xskeleton.module.ClientModule;
import com.mcgrady.xskeleton.module.GlobalConfigModule;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 含有每个组件都可公用的配置信息, 每个组件的 AndroidManifest 都应该声明此 ConfigModule
 *
 * @author: mcgrady
 * @date: 2018/12/20
 */
public final class GlobalConfiguration implements ConfigModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlobalConfigModule.Builder builder) {
        if (!BuildConfig.LOG_DEBUG) {
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        }

        builder.baseUrl(Api.APP_DOMAIN)
                .globalHttpHandler(new GlobalHttHandlerImpl(context))
                .responseErrorListener(new ResponseErrorListenerImpl())
                .gsonConfiguration(new AppModule.GsonConfiguration() {
                    @Override
                    public void configGson(@NonNull Context context, @NonNull GsonBuilder builder) {
                        builder.serializeNulls()    //序列化null
                                .enableComplexMapKeySerialization();
                    }
                })
                .retrofitConfiguration(new ClientModule.RetrofitConfiguration() {
                    @Override
                    public void configRetrofit(@NonNull Context context, @NonNull Retrofit.Builder builder) {
                        //自定义配置 Retrofit 的参数, 甚至您可以替换框架配置好的 OkHttpClient 对象 (但是不建议这样做, 这样做您将损失框架提供的很多功能)
                        // retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create());//比如使用 FastJson 替代 Gson
                    }
                })
                .okhttpConfiguration(new ClientModule.OkhttpConfiguration() {
                    @Override
                    public void configOkhttp(@NonNull Context context, @NonNull OkHttpClient.Builder builder) {
                        builder.writeTimeout(10, TimeUnit.SECONDS);
                        builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getTrustManager());
                        builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());

                        //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl. 详细使用请方法查看 https://github.com/JessYanCoding/RetrofitUrlManager
                        RetrofitUrlManager.getInstance().with(builder);
                    }
                })
                .cacheFile(FileUtils.getFileByPath(PathUtils.getExternalAppCachePath()))
                .cacheFactory(type -> {
                    //若想自定义 LruCache 的 size, 或者不想使用 LruCache, 想使用自己自定义的策略
                    //使用 GlobalConfigModule.Builder#cacheFactory() 即可扩展
                    switch (type.getCacheTypeId()) {
                        //Activity、Fragment 以及 Extras 使用 IntelligentCache (具有 LruCache 和 可永久存储数据的 Map)
                        case CacheType.EXTRAS_TYPE_ID:
                        case CacheType.ACTIVITY_CACHE_TYPE_ID:
                        case CacheType.FRAGMENT_CACHE_TYPE_ID:
                            return new IntelligentCache(type.calculateCacheSize(context));
                        //其余使用 LruCache (当达到最大容量时可根据 LRU 算法抛弃不合规数据)
                        default:
                            return new LruCache(type.calculateCacheSize(context));
                    }
                })
                .imageLoaderStrategy(new CommonGlideImageLoaderStrategy());

    }

    @Override
    public void injectAppLifecycle(@NonNull Context context, @NonNull List<AppLifecycles> lifecycles) {
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(@NonNull Context context, @NonNull List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }

    @Override
    public void injectFragmentLifecycle(@NonNull Context context, @NonNull List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentLifecycleCallbacksImpl());
    }
}
