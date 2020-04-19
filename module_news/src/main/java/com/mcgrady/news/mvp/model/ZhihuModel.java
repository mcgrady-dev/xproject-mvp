package com.mcgrady.news.mvp.model;

import com.mcgrady.common_core.http.BaseResponse;
import com.mcgrady.news.mvp.contract.ZhihuDailyDetailContract;
import com.mcgrady.news.mvp.contract.ZhihuDailyHomeContract;
import com.mcgrady.news.mvp.model.api.ZhihuService;
import com.mcgrady.news.mvp.model.entity.ZhihuDailyDetailBean;
import com.mcgrady.news.mvp.model.entity.ZhihuDailyStoriesBean;
import com.mcgrady.xskeleton.base.BaseModel;

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
public class ZhihuModel extends BaseModel implements ZhihuDailyHomeContract.Model, ZhihuDailyDetailContract.Model {

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Observable<ZhihuDailyStoriesBean> getDailyList() {
        return mRepositoryManager.obtainRetrofitService(ZhihuService.class)
                .getDailyList();
    }

    @Override
    public Observable<BaseResponse<ZhihuDailyStoriesBean>> getDailList2() {
        return mRepositoryManager.obtainRetrofitService(ZhihuService.class)
                .getDailyList2();
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