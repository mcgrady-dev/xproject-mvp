package com.mcgrady.core.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.blankj.utilcode.util.LogUtils;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/24
 * @des: 该基类只能放在子moudle中使用,用于moudle隔离
 */
public class BaseModuleApplication extends BaseApplication implements IBaseAppLifecycler {

    private static final String TAG = BaseModuleApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        onCreateAsLibrary(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        onLowMemoryAsLibrary(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        onTrimMemoryAsLibrary(this, level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        onTerminate(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onConfigurationChanged(this, newConfig);
    }

    @Override
    public void onTerminate(Application application) {
        LogUtils.i(TAG,this.getClass().getName()+" onTerminate");
    }

    @Override
    public void onCreateAsLibrary(Application application) {
        LogUtils.i(TAG,this.getClass().getName()+" onCreateAsLibrary");
    }

    @Override
    public void onLowMemoryAsLibrary(Application application) {
        LogUtils.i(TAG,this.getClass().getName()+" onLowMemoryAsLibrary");
    }

    @Override
    public void onTrimMemoryAsLibrary(Application application, int level) {
        LogUtils.i(TAG,this.getClass().getName()+" onTrimMemoryAsLibrary");
    }

    @Override
    public void onConfigurationChanged(Application application, Configuration configuration) {
        LogUtils.i(TAG,this.getClass().getName()+" onConfigurationChanged");
    }

    /**
     * arouter定义之前的初始化(可以忽略)
     */
    @Override
    public void init(Context context) {

    }
}
