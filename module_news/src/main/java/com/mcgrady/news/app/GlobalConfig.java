package com.mcgrady.news.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mcgrady.news.BuildConfig;
import com.mcgrady.xskeleton.base.AppComponent;
import com.mcgrady.xskeleton.base.AppLifecycles;
import com.mcgrady.xskeleton.integration.ConfigModule;
import com.mcgrady.xskeleton.integration.cache.IntelligentCache;
import com.mcgrady.xskeleton.module.GlobalConfigModule;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/12/20
 */

public class GlobalConfig implements ConfigModule {
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
    public void injectFragmentLifecycle(@NonNull Context context, @NonNull List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        if (BuildConfig.IS_BUILD_MODULE) {
            lifecycles.add(new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                    ((RefWatcher) AppComponent.obtainAppModule(f.getActivity())
                            .extras()
                            .get(IntelligentCache.getKeyOfKeep(RefWatcher.class.getName())))
                            .watch(f);
                }
            });
        }
    }
}
