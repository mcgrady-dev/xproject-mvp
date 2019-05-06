package com.mcgrady.main.di.component;

import com.mcgrady.main.di.module.CommonLoginModule;
import com.mcgrady.main.mvp.contract.CommonLoginContract;
import com.mcgrady.main.mvp.ui.activity.EyepetizerLoginActivity;
import com.mcgrady.main.mvp.ui.activity.MainLoginActivity;
import com.mcgrady.xskeleton.di.component.AppComponent;
import com.mcgrady.xskeleton.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = CommonLoginModule.class, dependencies = AppComponent.class)
public interface CommonLoginComponent {
    void inject(MainLoginActivity activity);

    void inject(EyepetizerLoginActivity activity);

//    void inject(XhsLoginFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CommonLoginComponent.Builder view(CommonLoginContract.View view);

        CommonLoginComponent.Builder appComponent(AppComponent appComponent);

        CommonLoginComponent build();
    }
}