package com.mcgrady.core.di.component;

import com.mcgrady.core.base.BaseApplication;
import com.mcgrady.core.di.module.AppModule;
import com.mcgrady.core.di.module.DataModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/24
 * @des:
 */
@Singleton
@Component(modules = {DataModule.class,AppModule.class})
public interface AppComponent {

    BaseApplication getContext();

//    HttpHelper httpHelper();
//
//    DBHelper dbHelper();
//
//    Random random();
}
