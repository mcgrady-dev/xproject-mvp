package com.mcgrady.xskeleton.utils;

import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.mcgrady.xskeleton.base.AppComponent;
import com.mcgrady.xskeleton.base.IApp;

/**
 * Created by mcgrady on 3/24/21.
 */
public final class XUtils {

    public static AppComponent obtainAppComponentFromContext() {
        Preconditions.checkNotNull(Utils.getApp(), "%s cannot be null", Context.class.getName());
        Preconditions.checkState(Utils.getApp() instanceof IApp, "%s must be implements %s", Utils.getApp().getApplicationContext().getClass().getName(), IApp.class.getName());
        return ((IApp) Utils.getApp().getApplicationContext()).getAppComponent();
    }
}
