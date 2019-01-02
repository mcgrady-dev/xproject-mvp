package com.mcgrady.common_core.utils;

import android.content.Context;

import com.mcgrady.common_core.base.delegate.IApp;
import com.mcgrady.common_core.di.component.AppComponent;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/2
 */

public class Utils {

    public static AppComponent obtainAppComponentFromContext(Context context) {
        Preconditions.checkNotNull(context, "%s cannot be null", Context.class.getName());
        Preconditions.checkState(context.getApplicationContext() instanceof IApp, "%s must be implements %s", context.getApplicationContext().getClass().getName(), IApp.class.getName());
        return ((IApp) context.getApplicationContext()).getAppComponent();
    }
}
