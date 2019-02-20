package com.mcgrady.main.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.mcgrady.common_core.di.scope.ActivityScope;
import com.mcgrady.common_core.manager.IRepositoryManager;
import com.mcgrady.common_core.mvp.BaseModel;
import com.mcgrady.main.mvp.contract.EyepetizerLoginContract;

import javax.inject.Inject;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/03/2019 16:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class EyepetizerLoginModel extends BaseModel implements EyepetizerLoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EyepetizerLoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}