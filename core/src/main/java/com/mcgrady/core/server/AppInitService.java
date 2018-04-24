package com.mcgrady.core.server;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.Utils;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/24
 * @des: 应用程序初始化服务
 */
public class AppInitService extends IntentService {
    private static final String ACTION_INIT = "initApplication";

    public AppInitService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AppInitService.class);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT.equals(action)) {
                initApplication();
            }
        }
    }

    private void initApplication() {

        // 初始化工具类
        Utils.init(this);

        // 初始化fragment框架
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(AppUtils.isAppDebug())    // 线上环境时，可能会遇到上述异常，此时debug=false，不会抛出该异常（避免crash），会捕获建议在回调处上传至我们的Crash检测服务器
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                }).install();

        // 初始化错误收集
//        CrashHandler.init(new CrashHandler(getApplicationContext()));

        // 初始化路由
        if (AppUtils.isAppDebug()) {
            // 打印日志
            ARouter.openLog();

            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        ARouter.init(this.getApplication());
    }

}
