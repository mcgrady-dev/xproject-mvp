package com.mcgrady.xskeleton.mvp;

import com.mcgrady.xskeleton.integration.IRepositoryManager;

/**
 * Created by mcgrady on 2019/4/25.
 */
public class BaseModel implements IModel {

    protected IRepositoryManager mRepositoryManager;

    public BaseModel(IRepositoryManager manager) {
        this.mRepositoryManager = manager;
    }

    @Override
    public void onDestory() {

    }
}
