package com.mcgrady.xproject.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mcgrady.core.base.BaseApplication;
import com.mcgrady.core.http.IHttpHelper;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/25
 * @des: 项目App入口
 */
public class App extends BaseApplication {

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @NonNull
    public static App app(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public IHttpHelper.NetConfig getNetConfig() {
        return new IHttpHelper.NetConfig().configBaseURL("http://static.owspace.com/");
    }
}
