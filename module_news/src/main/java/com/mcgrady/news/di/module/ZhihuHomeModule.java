package com.mcgrady.news.di.module;

import android.annotation.SuppressLint;

import com.mcgrady.common_core.di.scope.ActivityScope;
import com.mcgrady.news.mvp.contract.ZhihuHomeContract;
import com.mcgrady.news.mvp.model.ZhihuModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ZhihuHomeModule {

    @Binds
    abstract ZhihuHomeContract.Model bindZhihuHomeModel(ZhihuModel model);

    @SuppressLint("SimpleDateFormat")
    @ActivityScope
    @Provides
    static String provideString() {
        return "";
    }
}