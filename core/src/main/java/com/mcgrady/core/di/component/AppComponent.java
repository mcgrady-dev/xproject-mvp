package com.mcgrady.core.di.component;

import com.mcgrady.core.base.BaseApplication;
import com.mcgrady.core.di.module.AppModule;
import com.mcgrady.core.di.module.DataModule;
import com.mcgrady.core.http.HttpHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * <p></p>
 * @author: mcgrady
 * @date: 2018/5/9
 */
@Singleton
@Component(modules = {DataModule.class,AppModule.class})
public interface AppComponent {

    BaseApplication getContext();

    HttpHelper httpHelper();

//    DBHelper dbHelper();
//
//    Random random();
}
