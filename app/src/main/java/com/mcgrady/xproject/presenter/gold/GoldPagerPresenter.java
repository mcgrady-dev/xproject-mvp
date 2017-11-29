package com.mcgrady.xproject.presenter.gold;

import com.mcgrady.xproject.base.RxPresenter;
import com.mcgrady.xproject.model.DataManager;
import com.mcgrady.xproject.model.bean.GoldListBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by mcgrady on 2017/10/23.
 */

public class GoldPagerPresenter extends RxPresenter<GoldContract.View> implements GoldContract.Presenter {

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
//        Flowable<List<GoldListBean>> list = mDataManager.fetcjGoldList(type, NUM_EACH_PAGE, currentPage++)
    }

    @Override
    public void getMoreGoldData() {


    }
}
