package com.mcgrady.core.di;

import android.app.Application;
import android.content.Context;

import com.mcgrady.core.base.IAppLifecycles;
import com.mcgrady.core.di.component.AppComponent;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/9/30
 */

public class AppDelegate implements IAppLifecycles {

    private Application application;
    private AppComponent appComponent;

    @Inject
    @Named("ActivityLifecycle")
    protected Application.ActivityLifecycleCallbacks activityLifecycle;

    @Inject
    @Named("ActivityLifecycleForRxLifecycle")
    protected Application.ActivityLifecycleCallbacks mActivityLifecycleForRxLifecycle;

    public AppDelegate(Context context) {

    }

    @Override
    public void attachBaseContext(Context context) {

    }

    @Override
    public void onCreate(Application application) {

    }

    @Override
    public void onTerminate(Application application) {

    }
}
