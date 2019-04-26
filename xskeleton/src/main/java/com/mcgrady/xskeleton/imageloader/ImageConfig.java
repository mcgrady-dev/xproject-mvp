package com.mcgrady.xskeleton.imageloader;

import android.widget.ImageView;

/**
 * 图片加载配置信息基类,定义一些所有图片加载框架都可以用的通用参数
 * 每个 {@link BaseImageLoaderStrategy} 应该对应一个 {@link ImageConfig} 实现类
 *
 * Created by mcgrady on 2019/4/26.
 */
public class ImageConfig {
    protected String url;
    protected ImageView imageView;
    protected int placeholder;
    protected int errorPic;

    public String getUrl() { return url; }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getErrorPic() {
        return errorPic;
    }
}
