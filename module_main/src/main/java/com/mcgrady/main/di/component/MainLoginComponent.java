package com.mcgrady.main.di.component;

import com.mcgrady.main.di.module.MainLoginModule;
import com.mcgrady.main.mvp.contract.MainLoginContract;
import com.mcgrady.main.mvp.ui.activity.MainLoginActivity;
import com.mcgrady.xskeleton.di.component.AppComponent;
import com.mcgrady.xskeleton.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = MainLoginModule.class, dependencies = AppComponent.class)
public interface MainLoginComponent {
    void inject(MainLoginActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainLoginComponent.Builder view(MainLoginContract.View view);

        MainLoginComponent.Builder appComponent(AppComponent appComponent);

        MainLoginComponent build();
    }
}