package com.mcgrady.main.di.component;

import com.mcgrady.main.di.module.CommonRegisterModule;
import com.mcgrady.main.mvp.contract.CommonRegisterContract;
import com.mcgrady.main.mvp.ui.activity.MainRegisterActivity;
import com.mcgrady.xskeleton.di.component.AppComponent;
import com.mcgrady.xskeleton.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = CommonRegisterModule.class, dependencies = AppComponent.class)
public interface MainRegisterComponent {

    void inject(MainRegisterActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainRegisterComponent.Builder view(CommonRegisterContract.View view);

        MainRegisterComponent.Builder appComponent(AppComponent appComponent);

        MainRegisterComponent build();
    }
}