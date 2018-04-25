package com.mcgrady.xproject;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.mcgrady.core.base.BaseApplication;
import com.mcgrady.core.base.IBaseAppLifecycler;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/25
 * @des:
 */
public class XProjectApp extends BaseApplication implements IBaseAppLifecycler {

    @Autowired(name = "/main/MainApp")
    IBaseAppLifecycler mMainApp;
    @Autowired(name = "/news/NewsApp")
    IBaseAppLifecycler mNewsApp;

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onTerminate(Application application) {

    }

    @Override
    public void onCreateAsLibrary(Application application) {

    }

    @Override
    public void onLowMemoryAsLibrary(Application application) {

    }

    @Override
    public void onTrimMemoryAsLibrary(Application application, int level) {

    }

    @Override
    public void onConfigurationChanged(Application application, Configuration configuration) {

    }

    @Override
    public void init(Context context) {

    }
}
