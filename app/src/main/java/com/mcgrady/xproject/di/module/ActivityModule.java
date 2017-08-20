package com.mcgrady.xproject.di.module;

import android.app.Activity;

import com.mcgrady.xproject.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mcgrady on 2017/8/9.
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
