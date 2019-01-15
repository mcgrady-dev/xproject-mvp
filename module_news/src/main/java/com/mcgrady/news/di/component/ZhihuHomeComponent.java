package com.mcgrady.news.di.component;

import com.mcgrady.common_core.di.component.AppComponent;
import com.mcgrady.common_core.di.scope.ActivityScope;
import com.mcgrady.news.di.module.ZhihuHomeModule;
import com.mcgrady.news.mvp.contract.ZhihuHomeContract;
import com.mcgrady.news.mvp.ui.activity.ZhihuHomeActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/15/2019 13:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ZhihuHomeModule.class, dependencies = AppComponent.class)
public interface ZhihuHomeComponent {
    void inject(ZhihuHomeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ZhihuHomeComponent.Builder view(ZhihuHomeContract.View view);

        ZhihuHomeComponent.Builder appComponent(AppComponent appComponent);

        ZhihuHomeComponent build();
    }
}