package com.mcgrady.common_res.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.mcgrady.common_res.lifecycle.AppLifecyclesImpl;
import com.mcgrady.xskeleton.base.AppLifecycles;
import com.mcgrady.xskeleton.integration.ConfigModule;
import com.mcgrady.xskeleton.module.GlobalConfigModule;

import java.util.List;


/**
 * Created by mcgrady on 2020/4/28.
 */
public class GlobalConfiguration implements ConfigModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlobalConfigModule.Builder builder) {

    }

    @Override
    public void injectAppLifecycle(@NonNull Context context, @NonNull List<AppLifecycles> lifecycles) {
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(@NonNull Context context, @NonNull List<Application.ActivityLifecycleCallbacks> lifecycles) {
    }

    @Override
    public void injectFragmentLifecycle(@NonNull Context context, @NonNull List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
    }
}
