package com.mcgrady.news.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.mcgrady.common_core.di.scope.ActivityScope;
import com.mcgrady.common_core.manager.IRepositoryManager;
import com.mcgrady.common_core.mvp.BaseModel;
import com.mcgrady.news.mvp.contract.ZhihuDailyDetailContract;
import com.mcgrady.news.mvp.contract.ZhihuDailyHomeContract;
import com.mcgrady.news.mvp.model.api.ZhihuService;
import com.mcgrady.news.mvp.model.entity.ZhihuDailyStoriesBean;
import com.mcgrady.news.mvp.model.entity.ZhihuDailyDetailBean;

import javax.inject.Inject;

import io.reactivex.Observable;


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
public class ZhihuModel extends BaseModel implements ZhihuDailyHomeContract.Model, ZhihuDailyDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ZhihuModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<ZhihuDailyStoriesBean> getDailyList() {
        return mRepositoryManager.obtainRetrofitService(ZhihuService.class)
                .getDailyList();
    }

    @Override
    public Observable<ZhihuDailyStoriesBean> getBeforeDailyList(String date) {
        return mRepositoryManager.obtainRetrofitService(ZhihuService.class)
                .getBeforeDailyList(date);
    }

    @Override
    public Observable<ZhihuDailyDetailBean> getDailyDetail(int dailyId) {
        return mRepositoryManager.obtainRetrofitService(ZhihuService.class)
                .getDailyDetail(dailyId);
    }
}