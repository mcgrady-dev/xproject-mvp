package com.mcgrady.core.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.mcgrady.core.di.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/24
 * @des:
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return mFragment.getActivity();
    }
}
