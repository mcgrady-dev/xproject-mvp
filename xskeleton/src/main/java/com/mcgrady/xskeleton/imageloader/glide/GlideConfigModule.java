package com.mcgrady.xskeleton.imageloader.glide;

import android.content.Context;
import androidx.annotation.NonNull;

import com.blankj.utilcode.util.FileUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.mcgrady.xskeleton.di.component.AppComponent;
import com.mcgrady.xskeleton.http.OkHttpUrlLoader;
import com.mcgrady.xskeleton.imageloader.BaseImageLoaderStrategy;
import com.mcgrady.xskeleton.utils.Utils;

import java.io.File;
import java.io.InputStream;

/**
 * 用于配置缓存文件夹,切换图片请求框架等操作
 *
 * Created by mcgrady on 2019/4/26.
 */
@GlideModule(glideName = "XGlide")
public class GlideConfigModule extends AppGlideModule {
    public static final int IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024;//图片缓存文件最大值为100Mb

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        final AppComponent appComponent = Utils.obtainAppComponentFromContext(context);
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                // Careful: the external cache directory doesn't enforce permissions
                File cacheFile = new File(appComponent.cacheFile(), "Glide");
                FileUtils.createOrExistsDir(cacheFile);
                return DiskLruCacheWrapper.create(cacheFile, IMAGE_DISK_CACHE_MAX_SIZE);
            }
        });

        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        //将配置 Glide 的机会转交给 GlideImageLoaderStrategy,如你觉得框架提供的 GlideImageLoaderStrategy
        //并不能满足自己的需求,想自定义 BaseImageLoaderStrategy,那请你最好实现 GlideAppliesOptions
        //因为只有成为 GlideAppliesOptions 的实现类,这里才能调用 applyGlideOptions(),让你具有配置 Glide 的权利
        BaseImageLoaderStrategy loadImgStrategy = appComponent.imageLoader().getLoadImgStrategy();
        if (loadImgStrategy != null && loadImgStrategy instanceof GlideAppliesOptions) {
            ((GlideAppliesOptions) loadImgStrategy).applyGlideOptions(context, builder);
        }
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        //Glide 默认使用 HttpURLConnection 做网络请求,在这切换成 Okhttp 请求
        AppComponent appComponent = Utils.obtainAppComponentFromContext(context);
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(appComponent.okHttpClient()));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
