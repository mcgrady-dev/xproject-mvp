package com.mcgrady.main.di.component;

import com.mcgrady.main.di.module.MainRegisterModule;
import com.mcgrady.main.mvp.contract.MainRegisterContract;
import com.mcgrady.main.mvp.ui.activity.MainRegisterActivity;
import com.mcgrady.xskeleton.di.component.AppComponent;
import com.mcgrady.xskeleton.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = MainRegisterModule.class, dependencies = AppComponent.class)
public interface MainRegisterComponent {
    void inject(MainRegisterActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainRegisterComponent.Builder view(MainRegisterContract.View view);

        MainRegisterComponent.Builder appComponent(AppComponent appComponent);

        MainRegisterComponent build();
    }
}