package com.mcgrady.xproject.presenter.gold;

import com.mcgrady.xproject.base.RxPresenter;
import com.mcgrady.xproject.model.DataManager;
import com.mcgrady.xproject.model.bean.GoldListBean;
import com.mcgrady.xproject.model.http.response.GoldHttpResponse;
import com.mcgrady.xproject.util.RxUtil;
import com.mcgrady.xproject.widget.CommonSubscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by mcgrady on 2017/10/23.
 */

public class GoldPagerPresenter extends RxPresenter<GoldContract.View> implements GoldContract.Presenter {

    private static final int NUM_EACH_PAGE = 20;
    private static final int NUM_HOT_LIMIT = 3;

    private DataManager mDataManager;
    private List<GoldListBean> totalList = new ArrayList<>();
    private String type;
    private int currentPage = 0;
    private boolean isHotList = true;

    @Inject
    public GoldPagerPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void getGoldData(String type) {
        this.type = type;
        currentPage = 0;
        totalList.clear();
        Flowable<List<GoldListBean>> list = mDataManager.fetchGoldList(type, NUM_EACH_PAGE, currentPage++)
                .compose(RxUtil.<GoldHttpResponse<List<GoldListBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<GoldListBean>>handleGoldResult());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -3);

        Flowable<List<GoldListBean>> hotList = mDataManager.fetchGoldHotList(type,
                new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()), NUM_HOT_LIMIT)
                .compose(RxUtil.<GoldHttpResponse<List<GoldListBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<GoldListBean>>handleGoldResult());

        addSubscribe(Flowable.concat(hotList, list)
                .subscribeWith(new CommonSubscriber<List<GoldListBean>>(mView) {
                    @Override
                    public void onNext(List<GoldListBean> goldListBean) {
                        if (isHotList) {
                            isHotList = false;
                            totalList.addAll(goldListBean);
                        } else {
                            isHotList = true;
                            totalList.addAll(goldListBean);
                            mView.showContent(totalList);
                        }
                    }
                })
        );

    }

    @Override
    public void getMoreGoldData() {


    }
}
