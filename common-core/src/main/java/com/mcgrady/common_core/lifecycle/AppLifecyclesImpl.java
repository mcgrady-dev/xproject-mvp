package com.mcgrady.common_core.lifecycle;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.ToastQQStyle;
import com.mcgrady.common_core.BuildConfig;
import com.mcgrady.common_core.R;
import com.mcgrady.common_core.http.manager.RetrofitUrlManager;
import com.mcgrady.common_core.widget.MaterialHeader;
import com.mcgrady.xskeleton.base.AppLifecycles;
import com.mcgrady.xskeleton.integration.RepositoryManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import butterknife.ButterKnife;

/**
 * Created by mcgrady on 2019/5/14.
 */
public class AppLifecyclesImpl implements AppLifecycles {
    @Override
    public void attachBaseContext(@NonNull Context base) {
    }

    @Override
    public void onCreate(@NonNull Application application) {

        Utils.init(application);
        ToastUtils.init(application, new ToastQQStyle(application));
        RepositoryManager.init(application);

        if (BuildConfig.LOG_DEBUG) {
            ButterKnife.setDebug(true);
            ARouter.openDebug();
            ARouter.openLog();
            ARouter.printStackTrace();
            RetrofitUrlManager.getInstance().setDebug(true);
        }

        ARouter.init(application); // 尽可能早,推荐在Application中初始化



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
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);

            return new MaterialHeader(context);
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
//            layout.setReboundDuration(0);
            return new ClassicsFooter(context);
        });

//        AppUtils.registerAppStatusChangedListener(application, new Utils.OnAppStatusChangedListener() {
//            @Override
//            public void onForeground() {
//                LogUtils.i("App: " + application.getPackageName() + " is foreground");
//            }
//
//            @Override
//            public void onBackground() {
//                LogUtils.i("App: " + application.getPackageName() + " is background");
//            }
//        });
    }

    @Override
    public void onTerminate(@NonNull Application application) {
//        AppUtils.unregisterAppStatusChangedListener(application);
    }
}
