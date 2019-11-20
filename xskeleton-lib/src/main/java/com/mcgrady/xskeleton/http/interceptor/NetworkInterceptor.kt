package com.mcgrady.xskeleton.http.interceptor

import com.blankj.utilcode.util.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Incremental change is better than ambitious failure.
 *
 * Created by mcgrady on 2019-08-18.
 */
class NetworkInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetworkUtils.isConnected()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
        }
        val response = chain.proceed(request)
        if (NetworkUtils.isConnected()) { // 有网络时，设置超时为0
            val maxStale = 0
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=$maxStale")
                    .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build()
        } else { // 无网络时，设置超时为3周
            val maxStale = 60 * 60 * 24 * 21
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
        }
        return response
    }
}