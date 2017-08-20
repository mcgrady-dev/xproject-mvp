package com.mcgrady.xproject.di.component;

import android.app.Activity;

import com.mcgrady.xproject.di.module.ActivityModule;
import com.mcgrady.xproject.di.scope.ActivityScope;
import com.mcgrady.xproject.ui.main.activity.MainActivity;

import dagger.Component;

/**
 * Created by mcgrady on 2017/8/9.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MainActivity mainActivity);
}
