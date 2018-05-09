package com.mcgrady.core.di.module;

import android.app.Activity;

import com.mcgrady.core.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * <p></p>
 * @author: mcgrady
 * @date: 2018/5/9
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
