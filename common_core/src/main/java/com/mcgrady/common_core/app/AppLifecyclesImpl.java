package com.mcgrady.common_core.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.hjq.toast.ToastUtils;
import com.mcgrady.common_core.base.delegate.AppLifecycles;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/12/20
 */

public class AppLifecyclesImpl implements AppLifecycles {

    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    @Override
    public void onCreate(@NonNull Application application) {
        Utils.init(application);
        ToastUtils.init(application);
        ARouter.init(application);
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
