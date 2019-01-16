package com.mcgrady.common_core.config;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.hjq.toast.ToastUtils;
import com.mcgrady.common_core.BuildConfig;
import com.mcgrady.common_core.base.delegate.AppLifecycles;
import com.mcgrady.common_core.di.module.GlobalConfigModule;
import com.mcgrady.common_core.http.Api;
import com.mcgrady.common_core.http.GlobalHttpHandlerImpl;
import com.mcgrady.common_core.http.SSLSocketClient;
import com.mcgrady.common_core.http.imageloader.glide.GlideImageLoaderStrategy;
import com.mcgrady.common_core.http.log.RequestInterceptor;
import com.mcgrady.common_core.intergration.lifecycle.ActivityLifecycleCallbacksImpl;
import com.mcgrady.common_core.intergration.lifecycle.FragmentLifecycleCallbacksImpl;
import com.mcgrady.common_core.intergration.listener.ResponseErrorListenerImpl;
import com.mcgrady.common_core.intergration.manager.RetrofitUrlManager;

import java.util.List;

import butterknife.ButterKnife;

/**
 * <p></p>
 *
 * @author: mcgrady
 * @date: 2018/12/20
 */

public class AppConfig implements ConfigModule {

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        //Release 时,让框架不再打印 Http 请求和响应的信息
        if (!BuildConfig.LOG_DEBUG){
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        }

        builder.baseurl(Api.APP_DOMAIN)
                .imageLoaderStrategy(new GlideImageLoaderStrategy())
                .globalHttpHandler(new GlobalHttpHandlerImpl(context))
                .responseErrorListener(new ResponseErrorListenerImpl())
                .gsonConfiguration((context1, gsonBuilder) -> {
                    //这里可以自己自定义配置Gson的参数
                    gsonBuilder
                        .serializeNulls()//支持序列化null的参数
                        .enableComplexMapKeySerialization();//支持将序列化key为object的map,默认只能序列化key为string的map
                })
                .okhttpConfiguration((context12, builder1) -> {
                    builder1.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getTrustManager());
                    builder1.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
                    /**
                     * 让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl.
                     * 详细使用请方法查看 {@url https://github.com/JessYanCoding/RetrofitUrlManager}
                     */
                    RetrofitUrlManager.getInstance().with(builder1);
                })
                .rxCacheConfiguration((context1, rxCacheBuilder) -> {
                    //这里可以自己自定义配置RxCache的参数
                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
                    return null;
                });
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        // AppDelegate.Lifecycle 的所有方法都会在基类Application对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        lifecycles.add(new AppLifecycles() {
            @Override
            public void attachBaseContext(@NonNull Context base) {
            }

            @Override
            public void onCreate(@NonNull Application application) {
                if (BuildConfig.LOG_DEBUG) {
                    ButterKnife.setDebug(true);
                    ARouter.openLog();     // 打印日志
                    ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
                    RetrofitUrlManager.getInstance().setDebug(true);
                }

                ARouter.init(application); // 尽可能早,推荐在Application中初始化
                Utils.init(application);
                ToastUtils.init(application);
            }

            @Override
            public void onTerminate(@NonNull Application application) {
            }
        });
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentLifecycleCallbacksImpl());
    }
}
