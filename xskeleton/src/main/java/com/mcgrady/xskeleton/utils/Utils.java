package com.mcgrady.xskeleton.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.mcgrady.xskeleton.base.IApp;
import com.mcgrady.xskeleton.di.component.AppComponent;
import com.mcgrady.xskeleton.integration.exception.InvalidUrlException;

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

    public static AppComponent obtainAppComponentFromContext(Context context) {
        Preconditions.checkNotNull(context, "%s cannot be null", Context.class.getName());
        Preconditions.checkState(context.getApplicationContext() instanceof IApp, "%s must be implements %s", context.getApplicationContext().getClass().getName(), IApp.class.getName());
        return ((IApp) context.getApplicationContext()).getAppComponent();
    }

    /**
     * 获得资源
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * findview
     *
     * @param view
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(Context context, View view, String viewName) {
        int id = getResources(context).getIdentifier(viewName, "id", context.getPackageName());
        T v = (T) view.findViewById(id);
        return v;
    }

    /**
     * findview
     *
     * @param activity
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(Context context, Activity activity, String viewName) {
        int id = getResources(context).getIdentifier(viewName, "id", context.getPackageName());
        T v = (T) activity.findViewById(id);
        return v;
    }
}
