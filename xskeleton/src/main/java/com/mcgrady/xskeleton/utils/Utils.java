package com.mcgrady.xskeleton.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.util.Base64;
import android.util.Log;

import com.mcgrady.xskeleton.base.IApp;
import com.mcgrady.xskeleton.di.component.AppComponent;
import com.mcgrady.xskeleton.http.exception.InvalidUrlException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
     * 获取KeyHash码
     * @param context
     */
    public static void getKeyHash(Context context) {
        try {
            int i = 0;
            PackageInfo info = context.getApplicationContext().getPackageManager().getPackageInfo(
                    context.getApplicationContext().getPackageName(),  PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                i++;
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String KeyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                //KeyHash 就是你要的，不用改任何代码  复制粘贴 ;
                Log.e("KeyHash",KeyHash);
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
