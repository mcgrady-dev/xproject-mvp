package com.mcgrady.xproject.presenter;

import com.mcgrady.xproject.base.RxPresenter;
import com.mcgrady.xproject.contract.MainContract;
import com.mcgrady.xproject.model.DataManager;

import javax.inject.Inject;

/**
 * Created by mcgrady on 2017/8/9.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
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
