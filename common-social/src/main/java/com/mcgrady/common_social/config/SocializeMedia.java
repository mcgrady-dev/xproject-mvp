package com.mcgrady.common_social.config;

/**
 * Created by mcgrady on 2019/5/13.
 */
public enum SocializeMedia {

    GENERIC("generic"),
    SINA("sina"),
    QZONE("qzone"),
    QQ("qq"),
    WEIXIN("weixin"),
    WEIXIN_MONMENT("weixin_moment"),
    COPY("copy");

    private String name;

    SocializeMedia(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
