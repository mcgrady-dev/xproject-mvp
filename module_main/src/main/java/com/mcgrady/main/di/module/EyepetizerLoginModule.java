package com.mcgrady.main.di.module;

import com.mcgrady.main.mvp.contract.EyepetizerLoginContract;
import com.mcgrady.main.mvp.model.EyepetizerLoginModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class EyepetizerLoginModule {

    @Binds
    abstract EyepetizerLoginContract.Model bindEyepetizerLoginModel(EyepetizerLoginModel model);
}