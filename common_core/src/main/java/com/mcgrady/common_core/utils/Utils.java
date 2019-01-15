package com.mcgrady.common_core.utils;

import android.content.Context;

import com.mcgrady.common_core.base.delegate.IApp;
import com.mcgrady.common_core.di.component.AppComponent;
import com.mcgrady.common_core.intergration.exception.InvalidUrlException;

import okhttp3.HttpUrl;

/**
 * <p>工具类</p>
 *
 * @author: mcgrady
 * @date: 2019/1/2
 */

public class Utils {

    private Utils() {
        throw new IllegalStateException("do not instantiation me");
    }

    public static HttpUrl checkUrl(String url) {
        HttpUrl parseUrl = HttpUrl.parse(url);
        if (null == parseUrl) {
            throw new InvalidUrlException(url);
        } else {
            return parseUrl;
        }
    }

    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    public static AppComponent obtainAppComponentFromContext(Context context) {
        Preconditions.checkNotNull(context, "%s cannot be null", Context.class.getName());
        Preconditions.checkState(context.getApplicationContext() instanceof IApp, "%s must be implements %s", context.getApplicationContext().getClass().getName(), IApp.class.getName());
        return ((IApp) context.getApplicationContext()).getAppComponent();
    }
}
