package com.mcgrady.core.server;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * <p>应用程序初始化服务</p>
 *
 * @author: mcgrady
 * @date: 2018/5/9
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
    }

}
