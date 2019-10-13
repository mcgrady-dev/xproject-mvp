package com.mcgrady.xskeleton.base.delegate;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;

/**
 * 用于代理 {@link Application} 的生命周期
 *
 * Created by mcgrady on 2019/4/26.
 */
public interface AppLifecycles {

    void attachBaseContext(@NonNull Context base);

    void onCreate(@NonNull Application application);

    void onTerminate(@NonNull Application application);
}
