package com.mcgrady.xskeleton.http;

import okhttp3.HttpUrl;

/**
 * Created by mcgrady on 2019/4/26.
 */
public interface BaseUrl {

    /**
     * 在调用 Retrofit API 接口之前,使用 Okhttp 或其他方式,请求到正确的 BaseUrl 并通过此方法返回
     *
     * @return
     */
    HttpUrl url();
}
