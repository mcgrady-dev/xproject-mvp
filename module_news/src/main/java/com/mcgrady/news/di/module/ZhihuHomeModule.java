package com.mcgrady.news.di.module;

import com.mcgrady.news.mvp.contract.ZhihuHomeContract;
import com.mcgrady.news.mvp.model.ZhihuModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ZhihuHomeModule {

    @Binds
    abstract ZhihuHomeContract.Model bindZhihuHomeModel(ZhihuModel model);
}