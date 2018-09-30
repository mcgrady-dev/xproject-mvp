package com.mcgrady.main.persenter;

import com.mcgrady.core.base.BasePresenter;
import com.mcgrady.main.module.MainDataService;
import com.mcgrady.main.persenter.contract.MainContract;

import javax.inject.Inject;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/7/20
 */

public class MainPersenter extends BasePresenter<MainContract.IView> implements MainContract.IPresenter {

    private MainDataService mMainDataService;

    @Inject
    public MainPersenter(MainDataService mainDataService) {
        this.mMainDataService = mainDataService;
    }

    @Override
    public void loadOwspacePicList() {

    }
}
