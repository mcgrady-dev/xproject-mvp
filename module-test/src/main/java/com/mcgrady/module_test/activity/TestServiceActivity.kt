package com.mcgrady.module_test.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mcgrady.module_test.R
import com.mcgrady.module_test.service.LocationService

class TestServiceActivity : AppCompatActivity() {

    private val locationService: LocationService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_service)

        findViewById<Button>(R.id.btn_start).setOnClickListener {
//            var notification : Notification
//
//            var notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            var importance = NotificationManager.IMPORTANCE_HIGH
//
//            var audioManager : AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
//
//            var title = "前台服务通知标题";
//            var content = "前台服务通知内容";
//            if (Build.VERSION.SDK_INT >= 26) {
//                var id = "channel_id";  // 通知渠道的id
//                var name : CharSequence = "channelName";   // 用户可以看到的通知渠道的名字.
//                var description = "channelDesc";// 用户可以看到的通知渠道的描述
//                var channel = NotificationChannel(id, name, importance)
//                channel.setDescription(description);  // 配置通知渠道的属性
//                channel.enableLights(true); // 设置通知出现时的闪灯（如果 android 设备支持的话）
//                if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE
//                        || audioManager.getRingerMode() == AudioManager.MODE_NORMAL) {
//                    channel.enableVibration(true);  // 设置通知出现时的震动（如果 android 设备支持的话）
//                }
//                channel.vibrationPattern = longArrayOf(100L, 200L, 300L, 400L, 500L, 400L, 300L, 200L, 400L)
//                notificationManager.createNotificationChannel(channel)
//
//                notification = Notification.Builder(this, id)
//                        .setContentTitle(title)
//                        .setContentText(content)
//                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .build();
//            } else {
//                var defaults = Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND
//
//                //设置Notification.DEFAULT_VIBRATE的flag后可能会在任何 情况下都震动，部分系统的bug，所以要判断是否开启振动
//                if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE
//                        || audioManager.getRingerMode() == AudioManager.MODE_NORMAL) {
//                    defaults = defaults or Notification.DEFAULT_VIBRATE
//                }
//                notification = Notification.Builder(this)
//                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
//                        .setContentTitle(title)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentText(content)
//                        .setAutoCancel(true)
//                        .setDefaults(defaults)
//                        .getNotification()
//            }
//
//            startForegroundService(1, notification)
        }


//        ServiceConnection conn = new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                LocationService.LocalBinder binder = (LocationService.LocalBinder) service;
//                locationService = binder.getService();
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//                locationService = null;
//            }
//        };
//
//
//        Handler handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(@NonNull Message msg) {
//                return false;
//            }
//        });
    }
}
