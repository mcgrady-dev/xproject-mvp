package com.mcgrady.module_test.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Created by mcgrady on 2020/7/12.
 */
public class LocationService extends Service {

    private LocalBinder binder = new LocalBinder();
    private boolean quit;
    private int count;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!quit) {
                    try {
                        Thread.sleep(1000);
                        count++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        quit = false;
    }

    public int getCount() {
        return count;
    }

    public class LocalBinder extends Binder {

        LocationService getService() {
            return LocationService.this;
        }

    }
}
