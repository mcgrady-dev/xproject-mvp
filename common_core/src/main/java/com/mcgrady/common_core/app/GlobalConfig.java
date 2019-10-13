package com.mcgrady.common_core.app;

/**
 * 含有每个组件都可公用的配置信息, 每个组件的 AndroidManifest 都应该声明此 ConfigModule
 *
 * @author: mcgrady
 * @date: 2018/12/20
 */

/*public class GlobalConfig implements ConfigModule {

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {

        //Release 时,让框架不再打印 Http 请求和响应的信息
        if (!BuildConfig.LOG_DEBUG) {
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        }

        builder.baseurl(Api.APP_DOMAIN)
                .imageLoaderStrategy(new GlideImageLoaderStrategy())
                .globalHttpHandler(new GlobalHttpHandlerImpl(context))
                .responseErrorListener(new ResponseErrorListenerImpl())
                .gsonConfiguration(new AppModule.GsonConfig() {
                    @Override
                    public void configGson(@NonNull Context context, @NonNull GsonBuilder builder) {
                        //这里可以自己自定义配置Gson的参数
                        builder
                            .serializeNulls()//支持序列化null的参数
                            .enableComplexMapKeySerialization();//支持将序列化key为object的map,默认只能序列化key为string的map
                    }
                })
                .okhttpConfiguration(new ClientModule.OkhttpConfiguration() {
                    @Override
                    public void configOkhttp(@NonNull Context context, @NonNull OkHttpClient.Builder builder) {
                        builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getTrustManager());
                        builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
                        // 让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl.
                        // 详细使用请方法查看 {@url https://github.com/JessYanCoding/RetrofitUrlManager}
                        RetrofitUrlManager.getInstance().with(builder);
                    }
                })
                .rxCacheConfiguration((context1, rxCacheBuilder) -> {
                    //这里可以自己自定义配置RxCache的参数
                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
                    return null;
                });

        if (BuildConfig.DEBUG) {
            List<IKit> kits = new ArrayList<>();
            kits.add(new TestSwitchKit());
            DoraemonKit.install((Application) context, kits);
            DoraemonKit.hide();
        }
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        // AppDelegate.Lifecycle 的所有方法都会在基类Application对应的生命周期中被调用,
        // 所以在对应的方法中可以扩展一些自己需要的逻辑
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentLifecycleCallbacksImpl());
    }
}*/
