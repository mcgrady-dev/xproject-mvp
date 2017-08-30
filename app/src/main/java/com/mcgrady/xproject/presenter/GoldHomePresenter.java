package com.mcgrady.xproject.presenter;

import com.mcgrady.xproject.base.RxPresenter;
import com.mcgrady.xproject.contract.GoldHomeContract;

import javax.inject.Inject;

/**
 * Created by mcgrady on 2017/8/30.
 */

public class GoldHomePresenter extends RxPresenter<GoldHomeContract.View> implements GoldHomeContract.Presenter {

    @Inject
    public GoldHomePresenter() {
    }

    @Override
    public void attachView(GoldHomeContract.View view) {
        super.attachView(view);
    }


}
