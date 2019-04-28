package com.mcgrady.xskeleton.di.component;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.mcgrady.xskeleton.base.delegate.AppDelegate;
import com.mcgrady.xskeleton.cache.Cache;
import com.mcgrady.xskeleton.di.module.GlobalConfigModule;
import com.mcgrady.xskeleton.di.module.AppModule;
import com.mcgrady.xskeleton.di.module.ClientModule;
import com.mcgrady.xskeleton.imageloader.BaseImageLoaderStrategy;
import com.mcgrady.xskeleton.imageloader.ImageLoader;
import com.mcgrady.xskeleton.integration.ConfigModule;
import com.mcgrady.xskeleton.integration.IRepositoryManager;
import com.mcgrady.xskeleton.http.handler.RxErrorHandler;

import java.io.File;
import java.util.concurrent.ExecutorService;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by mcgrady on 2019/4/25.
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, GlobalConfigModule.class})
public interface AppComponent {
    Application application();

    IRepositoryManager repositoryManager();

    RxErrorHandler rxErrorHandler();

    OkHttpClient okHttpClient();

    Gson gson();

    /**
     * 图片加载管理器, 用于加载图片的管理类, 使用策略者模式, 可在运行时动态替换任何图片加载框架
     * 需在 {@link ConfigModule#applyOptions(Context, GlobalConfigModule.Builder)}
     * 中手动注册 {@link BaseImageLoaderStrategy}
     *
     * @return
     */
    ImageLoader imageLoader();

    /**
     * 缓存文件根目录
     * (RxCache 和 Glide 的缓存都已经作为子文件夹放在这个根目录下),
     * 应该将所有缓存都统一放到这个根目录下便于管理和清理, 可在{@link ConfigModule#applyOptions(Context, GlobalConfigModule.Builder)} 种配置
     * @return
     */
    File cacheFile();

    /**
     * 用来存取一些整个 App 公用的数据, 切勿大量存放大容量数据,
     * 这里的存放的数据和 {@link Application} 的生命周期一致
     * @return
     */
    Cache<String, Object> extras();

    /**
     * 创建框架所需缓存对象的工厂
     * @return
     */
    Cache.Factory cacheFactory();

    /**
     * 返回一个全局公用的线程池,适用于大多数异步需求。
     * 避免多个线程池创建带来的资源消耗。
     * @return
     */
    ExecutorService executorService();

    void inject(AppDelegate delegate);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder appConfigModule(GlobalConfigModule globalConfigModule);

        AppComponent build();
    }
}
