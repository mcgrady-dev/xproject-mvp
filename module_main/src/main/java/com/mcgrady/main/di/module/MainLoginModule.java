package com.mcgrady.main.di.module;


import com.mcgrady.main.mvp.contract.MainLoginContract;
import com.mcgrady.main.mvp.model.MainLoginModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainLoginModule {

    @Binds
    abstract MainLoginContract.Model bindMainLoginModel(MainLoginModel model);
}