package com.mcgrady.xproject.presenter;

import com.mcgrady.xproject.base.RxPresenter;
import com.mcgrady.xproject.contract.MainContract;

import javax.inject.Inject;

/**
 * Created by mcgrady on 2017/8/9.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter() {

    }

    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);

    }

    @Override
    public void testPresenter(String msg) {
        mView.showTestDialog(msg);

    }
}
