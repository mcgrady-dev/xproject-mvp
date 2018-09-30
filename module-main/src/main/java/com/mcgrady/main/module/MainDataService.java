package com.mcgrady.main.module;

import com.mcgrady.core.http.HttpHelper;
import com.mcgrady.main.persenter.contract.MainContract;

import javax.inject.Inject;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/9/20
 */

public class MainDataService implements MainContract.IModule {

    private HttpHelper mHttpHelper;

    @Inject
    public MainDataService(HttpHelper mHttpHelper) {
        this.mHttpHelper = mHttpHelper;
    }
}
