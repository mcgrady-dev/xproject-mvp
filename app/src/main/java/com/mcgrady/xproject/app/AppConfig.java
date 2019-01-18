package com.mcgrady.xproject.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.mcgrady.common_core.base.delegate.AppLifecycles;
import com.mcgrady.common_core.di.module.GlobalConfigModule;
import com.mcgrady.common_core.config.ConfigModule;

import java.util.List;

/**
 * <p>组件的全局配置信息在此配置, 需要将此实现类声明到 AndroidManifest 中
 *  common-core 中已有 {@link com.mcgrady.common_core.config.AppConfig} 配置有组件可公用的配置信息
 *  这里用来配置一些组件自身私有的配置信息</p>
 *
 * @author: mcgrady
 * @date: 2018/12/20
 */

public final class AppConfig implements ConfigModule {

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {

    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        /**
         * AppLifecycles 的所有方法都会在基类 Application 的对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
         * 可以根据不同的逻辑添加多个实现类
         */
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {

    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {

    }
}
