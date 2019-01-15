package com.mcgrady.news.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.mcgrady.common_core.base.delegate.AppLifecycles;
import com.mcgrady.common_core.di.module.GlobalConfigModule;
import com.mcgrady.common_core.intergration.cache.IntelligentCache;
import com.mcgrady.common_core.intergration.config.ConfigModule;
import com.mcgrady.common_core.utils.Utils;
import com.mcgrady.news.BuildConfig;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/12/20
 */

public class Config implements ConfigModule {
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {

    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {

    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        if (BuildConfig.IS_BUILD_MODULE) {
            lifecycles.add(new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                    ((RefWatcher) Utils.obtainAppComponentFromContext(f.getActivity())
                            .extras()
                            .get(IntelligentCache.getKeyOfKeep(RefWatcher.class.getName())))
                            .watch(f);
                }
            });
        }
    }
}
