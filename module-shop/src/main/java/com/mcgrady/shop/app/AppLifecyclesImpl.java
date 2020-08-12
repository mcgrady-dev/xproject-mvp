package com.mcgrady.shop.app;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/3/5
 */

/*public class AppLifecyclesImpl implements AppLifecycles {

    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    @Override
    public void onCreate(@NonNull Application application) {
        MultiDex.install(application);

        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        //使用 RetrofitUrlManager 切换 BaseUrl
        //RetrofitUrlManager.getInstance().putDomain(ZHIHU_DOMAIN_NAME, ZHIHU_DOMAIN);
        *//**
         * 当所有模块集成到宿主 App 时, 在{@link com.mcgrady.common_core.app.GlobalConfig}中已经执行了以下代码
         *//*
        if (BuildConfig.IS_BUILD_MODULE) {
            //leakCanary内存泄露检查
            Utils.obtainAppComponentFromContext(application).extras()
                    .put(IntelligentCache.getKeyOfKeep(RefWatcher.class.getName())
                            , BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);

            //启用矢量图兼容
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}*/
