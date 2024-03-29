package com.mcgrady.xproject.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.mcgrady.xskeleton.base.AppLifecycles;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/12/20
 */

public class AppLifecyclesImpl implements AppLifecycles {

    @Override
    public void attachBaseContext(@NonNull Context base) {
        MultiDex.install(base);
    }

    @Override
    public void onCreate(@NonNull Application application) {

//        if (LeakCanary.isInAnalyzerProcess(application)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//
//        //leakCanary内存泄露检查
//        Utils.obtainAppComponentFromContext(application).extras()
//                .put(IntelligentCache.getKeyOfKeep(RefWatcher.class.getName()),
//                    BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);
//
//        //启用矢量图兼容
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        //设置全局默认配置（优先级最低，会被其他设置覆盖）
//        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
//            //全局设置（优先级最低）
//            layout.setEnableAutoLoadMore(true);
//            layout.setEnableOverScrollDrag(false);
//            layout.setEnableOverScrollBounce(true);
//            layout.setEnableLoadMoreWhenContentNotFull(true);
//            layout.setEnableScrollContentWhenRefreshed(true);
//        });
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
//            //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
//            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
//
//            return new MaterialHeader(context);
//        });
//        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(context));
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
