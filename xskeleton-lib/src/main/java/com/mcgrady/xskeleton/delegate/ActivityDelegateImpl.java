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

        this.iActivity = null;
        this.activity = null;
    }
}
