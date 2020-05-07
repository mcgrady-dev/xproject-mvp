package com.mcgrady.xskeleton.http.imageloader;

import android.content.Context;

import androidx.annotation.Nullable;

/**
 * 图片加载策略
 * 通过 {@link ImageLoader#setLoadImgStrategy(BaseImageLoaderStrategy)} 配置
 * Created by mcgrady on 2020/5/4.
 */
public interface BaseImageLoaderStrategy<T extends ImageConfig> {

    /**
     * 加载图片
     *
     * @param ctx {@link Context}
     * @param config 图片加载配置信息
     */
    void loadImage(@Nullable Context ctx, @Nullable T config);

    /**
     * 停止加载
     *
     * @param ctx {@link Context}
     * @param config 图片加载配置信息
     */
    void clear(@Nullable Context ctx, @Nullable T config);
}
