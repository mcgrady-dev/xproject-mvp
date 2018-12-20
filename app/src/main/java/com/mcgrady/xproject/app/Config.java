package com.mcgrady.xproject.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.integration.ConfigModule;

import java.util.List;

/**
 * <p>组件的全局配置信息在此配置, 需要将此实现类声明到 AndroidManifest 中
 *  common-core 中已有 {@link com.mcgrady.core.app.Config} 配置有组件可公用的配置信息
 *  这里用来配置一些组件自身私有的配置信息</p>
 *
 * @author: mcgrady
 * @date: 2018/12/20
 */

public final class Config implements ConfigModule {

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {

    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {

    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {

    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {

    }
}
