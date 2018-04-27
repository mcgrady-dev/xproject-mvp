package com.mcgrady.core.base;

import android.app.Application;
import android.content.res.Configuration;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/24
 * @des: 作为接口，方便主工程调度子模块的声明周期
 */
public interface ApplicationLike extends IProvider {

    void onTerminate(Application application);

    void onCreateAsLibrary(Application application);

    void onLowMemoryAsLibrary(Application application);

    void onTrimMemoryAsLibrary(Application application, int level);

    void onConfigurationChanged(Application application, Configuration configuration);
}
