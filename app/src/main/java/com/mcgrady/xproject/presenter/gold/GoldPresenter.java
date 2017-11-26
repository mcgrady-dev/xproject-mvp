package com.mcgrady.xproject.presenter.gold;

import com.mcgrady.xproject.base.RxPresenter;
import com.mcgrady.xproject.model.DataManager;

import javax.inject.Inject;

/**
 * Created by mcgrady on 2017/10/23.
 */

public class GoldPresenter extends RxPresenter<GoldContract.View> implements GoldContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public GoldPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void getGoldData(String type) {

    }

    @Override
    public void getMoreGoldData() {

    }
}
