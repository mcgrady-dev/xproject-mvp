package com.mcgrady.xskeleton.integration;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.mcgrady.xskeleton.base.delegate.AppLifecycles;
import com.mcgrady.xskeleton.di.module.AppConfigModule;

import java.util.List;

/**
 * Created by mcgrady on 2019/4/25.
 */
public interface ConfigModule {

    /**
     * 使用 {@link AppConfigModule.Builder} 给框架配置一些配置参数
     *
     * @param context {@link Context}
     * @param builder {@link AppConfigModule.Builder}
     */
    void applyOptions(@NonNull Context context, @NonNull AppConfigModule.Builder builder);

    /**
     * 使用 {@link AppLifecycles} 在 {@link Application} 的生命周期中注入一些操作
     *
     * @param context    {@link Context}
     * @param lifecycles {@link Application} 的生命周期容器, 可向框架中添加多个 {@link Application} 的生命周期类
     */
    void injectAppLifecycle(@NonNull Context context, @NonNull List<AppLifecycles> lifecycles);

    /**
     * 使用 {@link Application.ActivityLifecycleCallbacks} 在 {@link android.app.Activity} 的生命周期中注入一些操作
     *
     * @param context    {@link Context}
     * @param lifecycles {@link android.app.Activity} 的生命周期容器, 可向框架中添加多个 {@link android.app.Activity} 的生命周期类
     */
    void injectActivityLifecycle(@NonNull Context context, @NonNull List<Application.ActivityLifecycleCallbacks> lifecycles);

    /**
     * 使用 {@link FragmentManager.FragmentLifecycleCallbacks} 在 {@link Fragment} 的生命周期中注入一些操作
     *
     * @param context    {@link Context}
     * @param lifecycles {@link Fragment} 的生命周期容器, 可向框架中添加多个 {@link Fragment} 的生命周期类
     */
    void injectFragmentLifecycle(@NonNull Context context, @NonNull List<FragmentManager.FragmentLifecycleCallbacks> lifecycles);
}
