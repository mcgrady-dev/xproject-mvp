package com.mcgrady.xproject;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.mcgrady.core.base.BaseApplication;
import com.mcgrady.core.base.IBaseAppLifecycler;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/25
 * @des: 项目App入口
 */
public class XProjectApp extends BaseApplication {

    @Autowired(name = "/main/MainApp")
    IBaseAppLifecycler mMainApp;
    @Autowired(name = "/news/NewsApp")
    IBaseAppLifecycler mNewsApp;
    public static synchronized BaseApplication getInstance() {
        return instance;
    }
}
