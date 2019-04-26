package com.mcgrady.xskeleton.imageloader;

import android.content.Context;
import android.support.annotation.Nullable;

/**
 * Created by mcgrady on 2019/4/26.
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
