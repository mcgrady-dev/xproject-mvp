package com.mcgrady.main.di.module;


import com.mcgrady.main.mvp.contract.CommonLoginContract;
import com.mcgrady.main.mvp.model.CommonLoginModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class CommonLoginModule {

    @Binds
    abstract CommonLoginContract.Model bindMainLoginModel(CommonLoginModel model);
}