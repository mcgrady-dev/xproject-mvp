package com.mcgrady.main.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.mcgrady.main.mvp.contract.CommonLoginContract;
import com.mcgrady.xskeleton.base.BaseModel;
import com.mcgrady.xskeleton.integration.IRepositoryManager;

public class CommonLoginModel extends BaseModel implements CommonLoginContract.Model {

    Gson mGson;
    Application mApplication;

    public CommonLoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}