package com.mcgrady.xskeleton.delegate;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mcgrady.xskeleton.base.IActivity;

/**
 * Created by mcgrady on 2020/4/23.
 */
public class ActivityDelegateImpl implements ActivityDelegate {

    private Activity activity;
    private IActivity iActivity;

    public ActivityDelegateImpl(@NonNull Activity activity) {
        this.activity = activity;
        this.iActivity = (IActivity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //如果要使用 EventBus 请将此方法返回 true
        if (iActivity.useEventBus()) {
            //注册到事件主线
//            EventBusManager.getInstance().register(activity);
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void onDestroy() {
        //如果要使用 EventBus 请将此方法返回 true
        if (iActivity != null && iActivity.useEventBus()) {
//            EventBusManager.getInstance().unregister(mActivity);
        }
        this.iActivity = null;
        this.activity = null;
    }
}
