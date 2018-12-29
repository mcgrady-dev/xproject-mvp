package com.mcgrady.core.http.interceptor;

import com.mcgrady.core.http.HttpHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>请求前后的操作</p>
 *
 * @author: mcgrady
 * @date: 2018/5/11
 */

public class CallInterceptor implements Interceptor {

    HttpHelper.RequestCall call;

    public CallInterceptor(HttpHelper.RequestCall call) {
        this.call = call;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (call != null) {
            if (call.onBeforeRequest(request, chain) != null) {
                request = call.onBeforeRequest(request, chain);
            }
        }
        Response response = chain.proceed(request);
        if (call != null) {
            //response.body()不能多次使用string方法
            if (call.onAfterRequest(response, response.body(), chain) != null) {
                response = call.onAfterRequest(response, response.body(), chain);
            }
        }
        return response;
    }
}
