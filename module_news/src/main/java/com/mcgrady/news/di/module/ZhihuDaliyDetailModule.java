package com.mcgrady.news.di.module;

import android.annotation.SuppressLint;

import com.mcgrady.common_core.di.scope.ActivityScope;
import com.mcgrady.news.mvp.contract.ZhihuDailyDetailContract;
import com.mcgrady.news.mvp.model.ZhihuModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ZhihuDaliyDetailModule {

    @Binds
    abstract ZhihuDailyDetailContract.Model bindZhihuDailyModel(ZhihuModel model);

    @SuppressLint("SimpleDateFormat")
    @ActivityScope
    @Provides
    static String provideString() {
        return "";
    }
}