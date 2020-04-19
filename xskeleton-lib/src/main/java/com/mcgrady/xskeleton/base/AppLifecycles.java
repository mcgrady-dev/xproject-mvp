package com.mcgrady.xskeleton.base;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Created by mcgrady on 2020/4/3.
 */
public interface AppLifecycles {

    void attachBaseContext(@NonNull Context base);

    void onCreate(@NonNull Application application);

    void onTerminate(@NonNull Application application);
}
