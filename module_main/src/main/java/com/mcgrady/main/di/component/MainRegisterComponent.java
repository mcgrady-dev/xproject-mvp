package com.mcgrady.main.di.component;

import com.mcgrady.common_core.di.component.AppComponent;
import com.mcgrady.common_core.di.scope.ActivityScope;
import com.mcgrady.main.di.module.MainRegisterModule;
import com.mcgrady.main.mvp.contract.MainRegisterContract;
import com.mcgrady.main.mvp.ui.activity.MainRegisterActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/21/2018 14:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
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