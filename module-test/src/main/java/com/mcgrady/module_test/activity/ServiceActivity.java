package com.mcgrady.module_test.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;

import com.mcgrady.module_test.IMyAidlInterface;
import com.mcgrady.module_test.R;

public class ServiceActivity extends AppCompatActivity {

    private IMyAidlInterface binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);



        ServiceConnection conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = IMyAidlInterface.Stub.asInterface(service);
                try {
                    int c = binder.call(1, 2);
//                    Log.d("remote service: call result=" + c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                binder = null;
            }
        };

        Intent intent = new Intent("com.mcgrady.module_test.CalService");
        intent.setPackage("com.mcgrady.module_test");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }
}