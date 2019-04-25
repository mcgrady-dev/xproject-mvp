package com.mcgrady.xskeleton.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by mcgrady on 2019/4/25.
 */
public class BaseApplication extends Application implements IApp {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


}
