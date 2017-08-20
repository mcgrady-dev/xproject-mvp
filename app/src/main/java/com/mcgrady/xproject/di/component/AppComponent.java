package com.mcgrady.xproject.di.component;

import com.mcgrady.xproject.app.App;
import com.mcgrady.xproject.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mcgrady on 2017/7/27.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    App getContext();       // 提供App的context

//    DataManager getDataManager();       // 数据管理中心
//
//    RetrofitHelper retrofitHelper();        // http请求帮助类
//
//    ImplPreferencesHelper preferencesHelper();      // sp帮助类
}
