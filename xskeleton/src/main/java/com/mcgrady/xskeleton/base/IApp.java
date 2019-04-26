package com.mcgrady.xskeleton.base;

import android.support.annotation.NonNull;

import com.mcgrady.xskeleton.di.component.AppComponent;

/**
 * Created by mcgrady on 2019/4/25.
 */
public interface IApp {
    @NonNull
    AppComponent getAppComponent();
}
