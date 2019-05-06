package com.mcgrady.main.di.module;


import com.mcgrady.main.mvp.contract.CommonRegisterContract;
import com.mcgrady.main.mvp.model.CommonRegisterModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class CommonRegisterModule {

    @Binds
    abstract CommonRegisterContract.Model bindMainRegisterModel(CommonRegisterModel model);
}