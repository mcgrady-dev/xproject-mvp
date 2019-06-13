package com.mcgrady.common_core.lifecycle;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.ToastAliPayStyle;
import com.mcgrady.common_core.BuildConfig;
import com.mcgrady.xskeleton.base.delegate.AppLifecycles;
import com.mcgrady.xskeleton.http.RetrofitUrlManager;

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
        if (BuildConfig.LOG_DEBUG) {
            ButterKnife.setDebug(true);
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            RetrofitUrlManager.getInstance().setDebug(true);
        }

        ARouter.init(application); // 尽可能早,推荐在Application中初始化
        Utils.init(application);
        ToastUtils.init(application);
        ToastUtils.initStyle(new ToastAliPayStyle());

        AppUtils.registerAppStatusChangedListener(application, new Utils.OnAppStatusChangedListener() {
            @Override
            public void onForeground() {
                LogUtils.i("App: " + application.getPackageName() + " is foreground");
            }

            @Override
            public void onBackground() {
                LogUtils.i("App: " + application.getPackageName() + " is background");
            }
        });
    }

    @Override
    public void onTerminate(@NonNull Application application) {
        AppUtils.unregisterAppStatusChangedListener(application);
    }
}
