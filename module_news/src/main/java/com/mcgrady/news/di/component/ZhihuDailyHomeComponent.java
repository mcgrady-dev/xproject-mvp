package com.mcgrady.news.di.component;

import com.mcgrady.news.di.module.ZhihuDailyHomeModule;
import com.mcgrady.news.mvp.contract.ZhihuDailyHomeContract;
import com.mcgrady.news.mvp.ui.activity.ZhihuDailyHomeActivity;
import com.mcgrady.xskeleton.di.component.AppComponent;
import com.mcgrady.xskeleton.di.scope.ActivityScope;

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
@Component(modules = ZhihuDailyHomeModule.class, dependencies = AppComponent.class)
public interface ZhihuDailyHomeComponent {

    void inject(ZhihuDailyHomeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ZhihuDailyHomeComponent.Builder view(ZhihuDailyHomeContract.View view);

        ZhihuDailyHomeComponent.Builder appComponent(AppComponent appComponent);

        ZhihuDailyHomeComponent build();
    }
}