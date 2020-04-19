package com.mcgrady.common_core.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.ToastAliPayStyle;
import com.mcgrady.common_core.BuildConfig;
import com.mcgrady.xskeleton.base.AppLifecycles;
import com.mcgrady.xskeleton.integration.RepositoryManager;

import butterknife.ButterKnife;

/**
 * Created by mcgrady on 2020/4/7.
 */
class AppLifecycleImpl implements AppLifecycles {
    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    @Override
    public void onCreate(@NonNull Application application) {
        if (BuildConfig.LOG_DEBUG) {

            Utils.init(application);
            ToastUtils.init(application, new ToastAliPayStyle(application));
            ButterKnife.setDebug(true);

            ARouter.openDebug();
            ARouter.openLog();
        }

        ARouter.init(application);

        RepositoryManager.init(application);
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
