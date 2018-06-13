package com.mcgrady.core.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.Utils;
import com.mcgrady.core.ActivitySwitchCallbacks;
import com.mcgrady.core.di.component.AppComponent;
import com.mcgrady.core.di.component.DaggerAppComponent;
import com.mcgrady.core.di.module.AppModule;
import com.mcgrady.core.di.module.DataModule;
import com.mcgrady.core.http.IHttpHelper;
import com.mcgrady.core.utils.ActivityStack;
import com.mcgrady.core.utils.AppContext;
import com.squareup.leakcanary.LeakCanary;

import me.yokeyword.fragmentation.Fragmentation;

/**
 * <p>app基类</p>
 *
 * @author: mcgrady
 * @date: 2018/5/9
 */
public class BaseApplication extends Application {

    protected static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppContext.init(this);

        // 初始化工具类
        Utils.init(this);

        /**
         * 初始化fragment框架
         *
         * {@link Fragmentation#debug}==false不会抛出该异常（避免crash），会捕获建议在回调处上传至我们的Crash检测服务器
         */
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(AppUtils.isAppDebug())
                .handleException(e -> {
                    // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                    // Bugtags.sendException(e);
                }).install();

        // 初始化路由
        if (AppUtils.isAppDebug()) {

            // 打印日志
            ARouter.openLog();

            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        ARouter.init(this);

        // 在子线程中完成初始化操作
        // AppInitService.start(this);

        registerActivityLifecycleCallbacks(new ActivitySwitchCallbacks());

        if (AppUtils.isAppDebug()) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }
    }

    public void exitApp() {
        ActivityStack.getInstance().clearAllActivity();
    }

    public AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .dataModule(new DataModule(getNetConfig()))
                .appModule(new AppModule(instance))
                .build();
    }

    public IHttpHelper.NetConfig getNetConfig() {
        return null;
    }
}
