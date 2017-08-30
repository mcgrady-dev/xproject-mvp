package com.mcgrady.xproject.di.component;

import android.app.Activity;

import com.mcgrady.xproject.di.module.FragmentModule;
import com.mcgrady.xproject.di.scope.FragmentScope;
import com.mcgrady.xproject.ui.gold.fragment.GoldHomeFragment;

import dagger.Component;

/**
 * Created by mcgrady on 2017/8/29.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(GoldHomeFragment goldHomeFragment);
}
