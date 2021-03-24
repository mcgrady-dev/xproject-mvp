package com.mcgrady.xproject.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.mcgrady.xskeleton.base.AppLifecycles;
import com.mcgrady.xskeleton.integration.ConfigModule;
import com.mcgrady.xskeleton.module.GlobalConfigModule;

import java.util.List;

/**
 * <p>组件的全局配置信息在此配置, 需要将此实现类声明到 AndroidManifest 中
 *  common-core 中已有 {@link GlobalConfig} 配置有组件可公用的配置信息
 *  这里用来配置一些组件自身私有的配置信息</p>
 *
 * @author: mcgrady
 * @date: 2018/12/20
 */

public final class GlobalConfig implements ConfigModule {

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
