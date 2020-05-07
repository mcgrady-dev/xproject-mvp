package com.mcgrady.common_core.lifecycle;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.ToastAliPayStyle;
import com.mcgrady.common_core.BuildConfig;
import com.mcgrady.common_core.http.manager.RetrofitUrlManager;
import com.mcgrady.xskeleton.base.AppLifecycles;
import com.mcgrady.xskeleton.integration.RepositoryManager;

import butterknife.ButterKnife;

/**
 * Created by mcgrady on 2019/5/14.
 */
public class AppLifecyclesImpl implements AppLifecycles {
    @Override
    public void attachBaseContext(@NonNull Context base) {
    }

    @Override
    public void onCreate(@NonNull Application application) {

        Utils.init(application);
        ToastUtils.init(application, new ToastAliPayStyle(application));
        ARouter.init(application); // 尽可能早,推荐在Application中初始化
        RepositoryManager.init(application);

        if (BuildConfig.LOG_DEBUG) {
            ButterKnife.setDebug(true);
            ARouter.openDebug();
            ARouter.openLog();
            RetrofitUrlManager.getInstance().setDebug(true);
        }

//        AppUtils.registerAppStatusChangedListener(application, new Utils.OnAppStatusChangedListener() {
//            @Override
//            public void onForeground() {
//                LogUtils.i("App: " + application.getPackageName() + " is foreground");
//            }
//
//            @Override
//            public void onBackground() {
//                LogUtils.i("App: " + application.getPackageName() + " is background");
//            }
//        });
    }

    @Override
    public void onTerminate(@NonNull Application application) {
//        AppUtils.unregisterAppStatusChangedListener(application);
    }
}
