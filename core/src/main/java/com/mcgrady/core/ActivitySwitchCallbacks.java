package com.mcgrady.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.mcgrady.core.utils.ActivityStack;

/**
 * <p></p>
 *
 * @author: mcgrady
 * @date: 2018/5/9
 */

public class ActivitySwitchCallbacks implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        ActivityStack.getInstance().pushActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityStack.getInstance().popActivity(activity);
    }
}
