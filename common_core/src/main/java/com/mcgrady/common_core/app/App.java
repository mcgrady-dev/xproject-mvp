package com.mcgrady.common_core.app;

import com.alibaba.android.arouter.launcher.ARouter;
import com.mcgrady.common_core.BuildConfig;
import com.mcgrady.xskeleton.base.BaseApplication;

import butterknife.ButterKnife;

/**
 * Created by mcgrady on 2019-12-23.
 */
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.LOG_DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }

        ARouter.init(this);

        ButterKnife.setDebug(BuildConfig.LOG_DEBUG);
    }
}
