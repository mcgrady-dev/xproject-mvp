package com.mcgrady.xproject.di.component;

import com.mcgrady.xproject.app.App;
import com.mcgrady.xproject.di.module.AppModule;
import com.mcgrady.xproject.di.module.HttpModule;
import com.mcgrady.xproject.model.DataManager;
import com.mcgrady.xproject.model.http.RetrofitHelper;
import com.mcgrady.xproject.model.prefs.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mcgrady on 2017/7/27.
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    App getContext();       // 提供App的context

    DataManager getDataManager();       // 数据管理中心

    RetrofitHelper retrofitHelper();        // http请求帮助类

    PreferencesHelper preferencesHelper();      // sp帮助类
}
