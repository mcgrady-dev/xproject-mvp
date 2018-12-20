package com.mcgrady.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mcgrady.main.di.module.LoginActivityModule;
import com.mcgrady.main.mvp.contract.LoginActivityContract;

import com.jess.arms.di.scope.ActivityScope;
import com.mcgrady.main.mvp.ui.activity.LoginActivityActivity;
import com.mcgrady.main.mvp.ui.fragment.LoginActivityFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/20/2018 15:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LoginActivityModule.class, dependencies = AppComponent.class)
public interface LoginActivityComponent {
    void inject(LoginActivityActivity activity);

    void inject(LoginActivityFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LoginActivityComponent.Builder view(LoginActivityContract.View view);

        LoginActivityComponent.Builder appComponent(AppComponent appComponent);

        LoginActivityComponent build();
    }
}