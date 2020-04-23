package com.mcgrady.xskeleton.base;

import android.content.Context;

import com.mcgrady.xskeleton.module.AppModule;
import com.mcgrady.xskeleton.module.ClientModule;
import com.mcgrady.xskeleton.utils.Preconditions;

/**
 * Created by mcgrady on 2020/4/7.
 */
public class AppComponent {

    public static AppModule obtainAppModule(Context context) {
        Preconditions.checkNotNull(context, "%s cannot be null", Context.class.getName());
        if (context instanceof IApp) {
            return ((IApp) context).getAppModule();
        } else {
            Preconditions.checkState(context.getApplicationContext() instanceof IApp,"%s must be implements %s", context.getApplicationContext().getClass().getName(), IApp.class.getName());
        }

        return ((IApp) context.getApplicationContext()).getAppModule();
    }

    public static ClientModule obtainClientModule(Context context) {
        Preconditions.checkNotNull(context, "%s cannot be null", Context.class.getName());
        if (context instanceof IApp) {
            return ((IApp) context).getClientModule();
        } else {
            Preconditions.checkState(context.getApplicationContext() instanceof IApp,"%s must be implements %s", context.getApplicationContext().getClass().getName(), IApp.class.getName());
        }

        return ((IApp) context.getApplicationContext()).getClientModule();
    }
}
