package com.mcgrady.news;

import android.app.Application;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mcgrady.core.base.BaseModuleApplication;
import com.mcgrady.core.base.IBaseAppLifecycler;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/25
 * @des:
 */
@Route(path = "/news/NewsApp")
public class NewsApp extends BaseModuleApplication implements IBaseAppLifecycler {

    @Override
    public void onCreateAsLibrary(Application application) {
        super.onCreateAsLibrary(application);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
