package com.mcgrady.xproject.zhihu.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.mcgrady.common_core.http.manager.RetrofitUrlManager;
import com.mcgrady.xproject.zhihu.BuildConfig;
import com.mcgrady.xproject.zhihu.mvp.model.api.Api;
import com.mcgrady.xskeleton.base.AppLifecycles;

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
//        MultiDex.install(application);

//        if (LeakCanary.isInAnalyzerProcess(application)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
        //使用 RetrofitUrlManager 切换 BaseUrl
        RetrofitUrlManager.getInstance().putDomain(Api.ZHIHU_DOMAIN_NAME, Api.ZHIHU_DOMAIN);

        //当所有模块集成到宿主 App 时, 在 GlobalConfig 中已经执行了以下代码
        if (BuildConfig.IS_BUILD_MODULE) {
            //leakCanary内存泄露检查
//            AppComponent.obtainAppModule(application)
//                    .extras()
//                    .put(IntelligentCache.getKeyOfKeep(RefWatcher.class.getName()),
//                            BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);
        }
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
