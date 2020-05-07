package com.mcgrady.xskeleton.http.imageloader;

import android.widget.ImageView;

/**
 * 图片加载配置信息的基类,定义一些所有图片加载框架都可以用的通用参数
 * Created by mcgrady on 2020/5/4.
 */
public class ImageConfig {
    protected String url;
    protected ImageView imageView;
    protected int placeholder;  //占位符
    protected int errorPic; //错误占位符

    public String getUrl() {
        return url;
    }

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
