package com.mcgrady.news.di.module;

import android.annotation.SuppressLint;

import com.mcgrady.news.mvp.contract.ZhihuDailyHomeContract;
import com.mcgrady.news.mvp.model.ZhihuModel;
import com.mcgrady.xskeleton.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ZhihuDailyHomeModule {

    @Binds
    abstract ZhihuDailyHomeContract.Model bindZhihuDaliyModel(ZhihuModel model);

    @SuppressLint("SimpleDateFormat")
    @ActivityScope
    @Provides
    static String provideString() {
        return "";
    }
}