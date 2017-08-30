package com.mcgrady.xproject.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.mcgrady.xproject.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mcgrady on 2017/8/9.
 */

@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
