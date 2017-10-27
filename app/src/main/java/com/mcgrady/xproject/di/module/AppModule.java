package com.mcgrady.xproject.di.module;

import com.mcgrady.xproject.app.App;
import com.mcgrady.xproject.model.DataManager;
import com.mcgrady.xproject.model.http.HttpHelper;
import com.mcgrady.xproject.model.http.RetrofitHelper;
import com.mcgrady.xproject.model.prefs.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mcgrady on 2017/7/27.
 */

@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(PreferencesHelper preferencesHelper) {
        return preferencesHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper, PreferencesHelper preferencesHelper) {
        return new DataManager(httpHelper, preferencesHelper);
    }
}
