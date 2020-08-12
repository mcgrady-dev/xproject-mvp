package com.mcgrady.common_core.http;

import android.content.Context;
import android.net.ParseException;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.mcgrady.common_core.utils.ViewUtils;
import com.mcgrady.xskeleton.http.interf.ResponseErrorListener;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * App 的全局配置信息在此配置, 需要将此实现类声明到 AndroidManifest 中
 * ConfigModule 的实现类可以有无数多个, 在 Application 中只是注册回调, 并不会影响性能 (多个 ConfigModule 在多 Module 环境下尤为受用)
 * ConfigModule 接口的实现类对象是通过反射生成的, 这里会有些性能损耗
 * Created by mcgrady on 2020/4/7.
 */
public class ResponseErrorListenerImpl implements ResponseErrorListener {

    @Override
    public void handlerResponseError(Context context, Throwable t) {
        LogUtils.wTag("Catch-Error", t);
        //这里不光只能打印错误, 还可以根据不同的错误做出不同的逻辑处理
        //这里只是对几个常用错误进行简单的处理, 展示这个类的用法, 在实际开发中请您自行对更多错误进行更严谨的处理
        String msg = null;
        if (t instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            msg = convertStatusCode(httpException);
        } else if (t instanceof ResponseException) {
            handleResponseCode((ResponseException) t);
            return;
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException || t instanceof JsonIOException) {
            msg = "数据解析错误";
        }

        if (!TextUtils.isEmpty(msg)) {
            ViewUtils.showSnackbar(msg, false);
        } else {
            LogUtils.d("未知错误");
        }

    }

    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }

    private void handleResponseCode(ResponseException exception) {
        switch (exception.getErrorCode()) {
            case Api.NOTLOGININ:
                //未登录
                break;
        }
    }
}
