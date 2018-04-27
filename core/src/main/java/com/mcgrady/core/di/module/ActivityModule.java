package com.mcgrady.core.di.module;

import android.app.Activity;

import com.mcgrady.core.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/24
 * @des:
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
