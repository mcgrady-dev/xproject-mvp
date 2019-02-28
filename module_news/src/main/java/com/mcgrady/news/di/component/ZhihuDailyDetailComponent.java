package com.mcgrady.news.di.component;

import com.mcgrady.common_core.di.component.AppComponent;
import com.mcgrady.common_core.di.scope.ActivityScope;
import com.mcgrady.news.di.module.ZhihuDaliyDetailModule;
import com.mcgrady.news.mvp.contract.ZhihuDailyDetailContract;
import com.mcgrady.news.mvp.ui.activity.ZhihuDailyDetailActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/2/28
 */
@ActivityScope
@Component(modules = ZhihuDaliyDetailModule.class, dependencies = AppComponent.class)
public interface ZhihuDailyDetailComponent {

    void inject(ZhihuDailyDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ZhihuDailyDetailComponent.Builder view(ZhihuDailyDetailContract.View view);

        ZhihuDailyDetailComponent.Builder appComponent(AppComponent appComponent);

        ZhihuDailyDetailComponent build();
    }
}
