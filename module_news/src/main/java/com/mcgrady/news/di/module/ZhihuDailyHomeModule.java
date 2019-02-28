package com.mcgrady.news.di.module;

import android.annotation.SuppressLint;

import com.mcgrady.common_core.di.scope.ActivityScope;
import com.mcgrady.news.mvp.contract.ZhihuDailyHomeContract;
import com.mcgrady.news.mvp.model.ZhihuModel;

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