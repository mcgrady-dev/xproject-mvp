package com.mcgrady.main.di.component;

import com.mcgrady.main.di.module.EyepetizerLoginModule;
import com.mcgrady.main.mvp.contract.EyepetizerLoginContract;
import com.mcgrady.main.mvp.ui.activity.EyepetizerLoginActivity;
import com.mcgrady.xskeleton.di.component.AppComponent;
import com.mcgrady.xskeleton.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = EyepetizerLoginModule.class, dependencies = AppComponent.class)
public interface EyepetizerLoginComponent {
    void inject(EyepetizerLoginActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EyepetizerLoginComponent.Builder view(EyepetizerLoginContract.View view);

        EyepetizerLoginComponent.Builder appComponent(AppComponent appComponent);

        EyepetizerLoginComponent build();
    }
}