package com.mcgrady.xskeleton.base;

import androidx.annotation.NonNull;

import com.mcgrady.xskeleton.module.AppModule;
import com.mcgrady.xskeleton.module.ClientModule;

/**
 * Created by mcgrady on 2020/4/3.
 */
public interface IApp {

    @NonNull
    ClientModule getClientModule();

    @NonNull
    AppModule getAppModule();
}
