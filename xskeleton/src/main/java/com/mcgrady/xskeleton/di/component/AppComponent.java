package com.mcgrady.xskeleton.di.component;

import android.app.Application;

import com.google.gson.Gson;
import com.mcgrady.xskeleton.integration.IRepositoryManager;

import java.io.File;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by mcgrady on 2019/4/25.
 */
@Singleton
public interface AppComponent {

    Application application();

    IRepositoryManager repositoryManager();

    OkHttpClient okhttpClient();

    Gson gson();

    File cacheFile();

//    void inject(AppDelegate delegate);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
//        Builder appConfigModule(AppConfigModule appConfigModule);

        AppComponent build();
    }
}
