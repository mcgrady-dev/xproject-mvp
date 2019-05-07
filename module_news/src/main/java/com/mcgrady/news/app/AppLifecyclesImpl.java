package com.mcgrady.news.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.mcgrady.common_res.widget.MaterialHeader;
import com.mcgrady.news.BuildConfig;
import com.mcgrady.news.mvp.model.api.Api;
import com.mcgrady.xskeleton.base.delegate.AppLifecycles;
import com.mcgrady.xskeleton.cache.IntelligentCache;
import com.mcgrady.xskeleton.http.RetrofitUrlManager;
import com.mcgrady.xskeleton.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * <p>类说明</p>
 *
 * @author mcgrady
 * @date 2019/1/15
 */
public class AppLifecyclesImpl implements AppLifecycles {


    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    @Override
    public void onCreate(@NonNull Application application) {
        MultiDex.install(application);

        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        //使用 RetrofitUrlManager 切换 BaseUrl
        RetrofitUrlManager.getInstance().putDomain(Api.ZHIHU_DOMAIN_NAME, Api.ZHIHU_DOMAIN);
        /**
         * 当所有模块集成到宿主 App 时, 在 GlobalConfig 中已经执行了以下代码
         */
        if (!BuildConfig.IS_BUILD_MODULE) {
            //leakCanary内存泄露检查
            Utils.obtainAppComponentFromContext(application).extras()
                    .put(IntelligentCache.getKeyOfKeep(RefWatcher.class.getName())
                            , BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);

            //启用矢量图兼容
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
            //设置全局默认配置（优先级最低，会被其他设置覆盖）
            SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
                //全局设置（优先级最低）
                layout.setEnableAutoLoadMore(true);
                layout.setEnableOverScrollDrag(false);
                layout.setEnableOverScrollBounce(true);
                layout.setEnableLoadMoreWhenContentNotFull(true);
                layout.setEnableScrollContentWhenRefreshed(true);
            });
            SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
                //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
                layout.setPrimaryColorsId(android.R.color.white);

                return new MaterialHeader(context);
            });
            SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new BallPulseFooter(context));
        }
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
