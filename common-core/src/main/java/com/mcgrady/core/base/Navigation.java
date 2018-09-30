package com.mcgrady.core.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/9/20
 */

public class Navigation {

    public void startActivity(Context context, String url, Bundle bundle) {
        startActivity(context, url, bundle, -1);
    }

    public static void startActivity(Context context, String url, Bundle bundle, int requestCode) {

        Postcard postcard = ARouter.getInstance().build(url).with(bundle);
        if (context instanceof Activity) {
            postcard.navigation((Activity) context, requestCode);
        } else {
            postcard.navigation(context);
        }
    }
}
