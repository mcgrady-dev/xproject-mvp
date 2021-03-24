package com.mcgrady.xproject.zhihu.mvp.model;

import com.mcgrady.common_core.http.BaseResponse;
import com.mcgrady.xproject.zhihu.mvp.contract.ZhihuDailyDetailContract;
import com.mcgrady.xproject.zhihu.mvp.contract.ZhihuDailyHomeContract;
import com.mcgrady.xproject.zhihu.mvp.model.api.ZhihuService;
import com.mcgrady.xproject.zhihu.mvp.model.entity.ZhihuDailyDetailBean;
import com.mcgrady.xproject.zhihu.mvp.model.entity.ZhihuDailyStoriesBean;
import com.mcgrady.xskeleton.base.BaseModel;
import com.mcgrady.xskeleton.integration.IRepositoryManager;
import com.mcgrady.xskeleton.utils.XUtils;

import io.reactivex.Observable;


public class ZhihuModel extends BaseModel implements ZhihuDailyHomeContract.Model, ZhihuDailyDetailContract.Model {

    public ZhihuModel() {
        this(XUtils.obtainAppComponentFromContext().repositoryManager());
    }

    public ZhihuModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Observable<ZhihuDailyStoriesBean> getDailyList() {
        return repositoryManager.obtainRetrofitService(ZhihuService.class)
                .getDailyList();
    }

    @Override
    public Observable<BaseResponse<ZhihuDailyStoriesBean>> getDailList2() {
        return repositoryManager.obtainRetrofitService(ZhihuService.class)
                .getDailyList2();
    }

    @Override
    public Observable<ZhihuDailyStoriesBean> getBeforeDailyList(String date) {
        return repositoryManager.obtainRetrofitService(ZhihuService.class)
                .getBeforeDailyList(date);
    }

    @Override
    public Observable<ZhihuDailyDetailBean> getDailyDetail(int dailyId) {
        return repositoryManager.obtainRetrofitService(ZhihuService.class)
                .getDailyDetail(dailyId);
    }
}