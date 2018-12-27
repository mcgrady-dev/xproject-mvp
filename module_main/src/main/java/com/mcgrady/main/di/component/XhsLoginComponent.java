package com.mcgrady.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mcgrady.main.di.module.XhsLoginModule;
import com.mcgrady.main.mvp.contract.XhsLoginContract;

import com.jess.arms.di.scope.FragmentScope;
import com.mcgrady.main.mvp.ui.fragment.XhsLoginFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/21/2018 14:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = XhsLoginModule.class, dependencies = AppComponent.class)
public interface XhsLoginComponent {
    void inject(XhsLoginFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        XhsLoginComponent.Builder view(XhsLoginContract.View view);

        XhsLoginComponent.Builder appComponent(AppComponent appComponent);

        XhsLoginComponent build();
    }
}