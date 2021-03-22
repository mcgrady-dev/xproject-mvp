package com.mcgrady.xupdatemanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.mcgrady.xupdatemanager.entity.UpdateDownloadEntity;
import com.mcgrady.xupdatemanager.entity.UpdateEntity;
import com.mcgrady.xupdatemanager.interf.IUpdateDownloadCallback;
import com.mcgrady.xupdatemanager.util.Utils;

import java.io.File;

/**
 * APK 下载服务
 *
 * @author: mcgrady
 * @date: 2019-05-28
 */
public class ApkDownloadService extends Service {

    private static final int DOWNLOAD_NOTIFY_ID = 1000;
    private static final String CHANNEL_ID = "xupdate_channel_id";
    private static final CharSequence CHANNEL_NAME = "xupdate_channel_name";

    public static boolean sIsRunning = false;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;

    public static void bindService(Context context, ServiceConnection connection) {
        Intent intent = new Intent(context, ApkDownloadService.class);
        context.startService(intent);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        sIsRunning = true;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        sIsRunning = true;
        return new DownloadBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        sIsRunning = false;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        notificationManager = null;
        builder = null;
        super.onDestroy();
    }

    private void setNotification(@NonNull UpdateDownloadEntity downloadEntity) {
        if (!downloadEntity.isNotification()) {
            return;
        }

        initNotification();
    }

    private void initNotification() {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
//            //设置绕过免打扰模式
////            channel.setBypassDnd(false);
////            //检测是否绕过免打扰模式
////            channel.canBypassDnd();
////            //设置在锁屏界面上显示这条通知
////            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
////            channel.setLightColor(Color.GREEN);
////            channel.setShowBadge(true);
////            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//            channel.enableVibration(false);
//            channel.enableLights(false);
//
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        builder = getNotificationBuilder();
//        notificationManager.notify(DOWNLOAD_NOTIFY_ID, builder.build());
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("开始下载")
                .setContentText("正在连接服务器...")
                .setSmallIcon(R.mipmap.xupdate_icon_app_update)
                .setLargeIcon(Utils.drawable2Bitmap(Utils.getAppIcon(ApkDownloadService.this)))
                .setOngoing(true)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis());
    }

    private void startDownload(@NonNull UpdateEntity updateEntity, @NonNull IUpdateDownloadCallback downloadCallback) {
        String apkUrl = updateEntity.getDownloadEntity().getDownloadUrl();
        if (TextUtils.isEmpty(apkUrl)) {
            String msg = "New version download path error";
            stop(msg);
            return;
        }

        String apkName = Utils.getApkNameByDownloadUrl(apkUrl);

        File apkCacheDir = new File(updateEntity.getDownloadEntity().getCacheDir());
        if (!apkCacheDir.exists()) {
            apkCacheDir.mkdirs();
        }

        String target = apkCacheDir + File.separator + updateEntity.getVersionName();

        updateEntity.getUpdateHttpClient().download(apkUrl, target, apkName, downloadCallback);
    }

    private void stop(String content) {
        if (builder != null) {
            builder.setContentTitle(Utils.getAppName(ApkDownloadService.this))
                    .setContentText(content);
            Notification notification = builder.build();
            notification.flags = notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(DOWNLOAD_NOTIFY_ID, notification);
        }

        close();
    }
    private void close() {
        sIsRunning = false;
        stopSelf();
    }

    public class DownloadBinder extends Binder {

        private IUpdateDownloadCallback downloadCallback;
        private UpdateEntity updateEntity;
        private boolean isCancel = false;
        private int curRate;

        public void start(@NonNull UpdateEntity updateEntity, @NonNull IUpdateDownloadCallback downloadCallback) {
            this.updateEntity = updateEntity;
            this.downloadCallback = downloadCallback;
            startDownload(updateEntity, new IUpdateDownloadCallback() {
                @Override
                public void onStart() {
                    if (isCancel) {
                        return;
                    }

                    notificationManager.cancel(DOWNLOAD_NOTIFY_ID);
                    builder = null;
                    setNotification(updateEntity.getDownloadEntity());

                    downloadCallback.onStart();
                }

                @Override
                public void onProgress(float progress, long total) {
                    if (isCancel) {
                        return;
                    }

                    //做一下判断，防止自回调过于频繁，造成更新通知栏进度过于频繁，而出现卡顿的问题。
                    int rate = Math.round(progress * 100);
                    if (curRate != rate) {
                        if (downloadCallback != null) {
                            downloadCallback.onProgress(progress, total);
                        }

                        if (builder != null) {
                            builder.setContentTitle("Downloading: " + Utils.getAppName(ApkDownloadService.this))
                                    .setContentText(rate + "%")
                                    .setProgress(100, rate, false)
                                    .setWhen(System.currentTimeMillis());
                            Notification notification = builder.build();
                            notification.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONLY_ALERT_ONCE;
                            notificationManager.notify(DOWNLOAD_NOTIFY_ID, notification);
                        }
                        //重新赋值
                        curRate = rate;
                    }
                }

                @Override
                public boolean onCompleted(File file) {
                    if (isCancel) {
                        return false;
                    }
                    return downloadCallback.onCompleted(file);
                }

                @Override
                public void onError(Throwable throwable) {
                    downloadCallback.onError(throwable);
                }
            });
        }

        public void stop(String msg) {
            if (downloadCallback != null) {
                downloadCallback = null;
            }

            updateEntity.getUpdateHttpClient().cancelDownload(updateEntity.getDownloadEntity().getDownloadUrl());
            ApkDownloadService.this.stop(msg);
        }
    }
}
