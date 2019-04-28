package com.mcgrady.main.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.mcgrady.xskeleton.base.delegate.AppLifecycles;


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

    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
