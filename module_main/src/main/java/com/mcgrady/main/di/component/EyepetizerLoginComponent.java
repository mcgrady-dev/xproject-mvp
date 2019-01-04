package com.mcgrady.main.di.component;

import com.mcgrady.common_core.di.component.AppComponent;
import com.mcgrady.common_core.di.scope.ActivityScope;
import com.mcgrady.main.di.module.EyepetizerLoginModule;
import com.mcgrady.main.mvp.contract.EyepetizerLoginContract;
import com.mcgrady.main.mvp.ui.activity.EyepetizerLoginActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/03/2019 16:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
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