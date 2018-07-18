package com.mcgrady.core.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.mcgrady.core.di.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * <p></p>
 * @author: mcgrady
 * @date: 2018/5/9
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
