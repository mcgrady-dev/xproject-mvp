package com.mcgrady.main.di.module;


import com.mcgrady.main.mvp.contract.MainRegisterContract;
import com.mcgrady.main.mvp.model.MainRegisterModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainRegisterModule {

    @Binds
    abstract MainRegisterContract.Model bindMainRegisterModel(MainRegisterModel model);
}