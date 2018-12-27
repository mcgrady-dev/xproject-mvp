package com.mcgrady.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mcgrady.main.di.module.XhsWelcomeModule;
import com.mcgrady.main.mvp.contract.XhsWelcomeContract;

import com.jess.arms.di.scope.FragmentScope;
import com.mcgrady.main.mvp.ui.fragment.XhsWelcomeFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/21/2018 14:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = XhsWelcomeModule.class, dependencies = AppComponent.class)
public interface XhsWelcomeComponent {
    void inject(XhsWelcomeFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        XhsWelcomeComponent.Builder view(XhsWelcomeContract.View view);

        XhsWelcomeComponent.Builder appComponent(AppComponent appComponent);

        XhsWelcomeComponent build();
    }
}