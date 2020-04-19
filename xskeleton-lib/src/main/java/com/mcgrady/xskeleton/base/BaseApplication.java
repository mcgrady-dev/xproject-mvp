package com.mcgrady.xskeleton.base;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.mcgrady.xskeleton.integration.ConfigModule;
import com.mcgrady.xskeleton.integration.ManifestParser;
import com.mcgrady.xskeleton.module.AppModule;
import com.mcgrady.xskeleton.module.ClientModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcgrady on 2019-09-16.
 */
public class BaseApplication extends Application implements IApp {

    private AppDelegate appDelegate;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        List<AppLifecycles> appLifecycles = new ArrayList<>();
        List<Application.ActivityLifecycleCallbacks> activityLifecycles = new ArrayList<>();
        List<ConfigModule> modules = new ManifestParser(base).parse();
        for (ConfigModule module : modules) {
            module.injectAppLifecycle(base, appLifecycles);
            module.injectActivityLifecycle(base, activityLifecycles);
        }

        if (appDelegate == null) {
            appDelegate = new AppDelegate(base);
        }
        appDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (appDelegate != null) {
            appDelegate.onCreate(this);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (appDelegate != null) {
            appDelegate.onTerminate(this);
        }
    }

    @NonNull
    @Override
    public ClientModule getClientModule() {
        return appDelegate != null ? appDelegate.getClientModule() : null;
    }

    @NonNull
    @Override
    public AppModule getAppModule() {
        return appDelegate != null ? appDelegate.getAppModule() : null;
    }
}
