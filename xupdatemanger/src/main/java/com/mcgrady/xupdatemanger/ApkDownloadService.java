package com.mcgrady.xupdatemanger;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * APK 下载服务
 *
 * @author: mcgrady
 * @date: 2019-05-28
 */
public class ApkDownloadService extends Service {

    public static boolean isRunning = false;
    private NotificationManager notificationManager;
    private DownloadBinder binder = new DownloadBinder();
    private NotificationCompat.Builder builder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
