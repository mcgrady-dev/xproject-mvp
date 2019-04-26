package com.mcgrady.news.di.module;

import android.annotation.SuppressLint;

import com.mcgrady.news.mvp.contract.ZhihuDailyDetailContract;
import com.mcgrady.news.mvp.model.ZhihuModel;
import com.mcgrady.xskeleton.di.scope.ActivityScope;

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