package com.mcgrady.xskeleton.base;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.mcgrady.xskeleton.utils.Preconditions;

/**
 * Created by mcgrady on 2019-09-16.
 */
public class BaseApplication extends Application implements IApp {

    private AppDelegate appDelegate;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        if (appDelegate == null) {
            appDelegate = new AppDelegate(base);
        }
        appDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (appDelegate != null) {
            appDelegate.onCreate(this);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (appDelegate != null) {
            appDelegate.onTerminate(this);
        }
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(appDelegate, "%s cannot be null", AppDelegate.class.getName());
        Preconditions.checkState(appDelegate instanceof IApp, "%s must be implements %s", appDelegate.getClass().getName(), IApp.class.getName());
        return ((IApp) appDelegate).getAppComponent();
    }
}
