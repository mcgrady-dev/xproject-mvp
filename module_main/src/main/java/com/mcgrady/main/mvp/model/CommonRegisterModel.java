package com.mcgrady.main.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.mcgrady.main.mvp.contract.CommonRegisterContract;
import com.mcgrady.xskeleton.base.BaseModel;
import com.mcgrady.xskeleton.integration.IRepositoryManager;

public class CommonRegisterModel extends BaseModel implements CommonRegisterContract.Model {
    Gson mGson;
    Application mApplication;

    public CommonRegisterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}