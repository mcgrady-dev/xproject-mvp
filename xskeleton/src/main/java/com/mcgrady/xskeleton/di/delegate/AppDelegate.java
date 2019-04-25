package com.mcgrady.xskeleton.di.delegate;

import android.app.Application;

import com.mcgrady.xskeleton.base.IApp;
import com.mcgrady.xskeleton.di.component.AppComponent;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by mcgrady on 2019/4/25.
 */
public class AppDelegate implements IApp {

    private Application mApplication;
    private AppComponent mAppComponent;

    @Inject
    @Named("ActivityLifecycle")
    protected Application.ActivityLifecycleCallbacks mActivityLifecycle;
    @Inject
    @Named("ActivityRxLifecycle")
    protected Application.ActivityLifecycleCallbacks mActivityRxLifecycle;
}
