package com.mcgrady.main.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.mcgrady.xskeleton.base.AppLifecycles;
import com.mcgrady.xskeleton.integration.ConfigModule;
import com.mcgrady.xskeleton.module.GlobalConfigModule;

import java.util.List;

/**
 * Created by mcgrady on 2020/4/3.
 */
public class GlobalConfig implements ConfigModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlobalConfigModule.Builder builder) {

    }

    @Override
    public void injectAppLifecycle(@NonNull Context context, @NonNull List<AppLifecycles> lifecycles) {

    }

    @Override
    public void injectActivityLifecycle(@NonNull Context context, @NonNull List<Application.ActivityLifecycleCallbacks> lifecycles) {

    }

    @Override
    public void injectFragmentLifecycle(@NonNull Context context, @NonNull List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {

    }
}
