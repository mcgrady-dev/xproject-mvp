package com.mcgrady.core.http.interceptor;

import android.accounts.AccountManager;
import android.text.TextUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>Token拦截器</p>
 *
 * @author: mcgrady
 * @date: 2018/5/10
 */

public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        //请求定制：添加请求头
        Request.Builder requestBuilder = original.newBuilder();
        String access_token = AccountManager.INSTANCE.getAccessToken();
        if(!TextUtils.isEmpty(access_token)){
            requestBuilder.addHeader("Authorization", access_token);
        }

        requestBuilder.addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");

        //请求体定制：统一添加token参数
        if (original.body() instanceof FormBody) {
            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oidFormBody = (FormBody) original.body();
            for (int i = 0; i < oidFormBody.size(); i++) {
                newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
            }
            requestBuilder.method(original.method(), newFormBody.build());
        }

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
