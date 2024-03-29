package com.mcgrady.xproject.main.mvp.model;

import com.mcgrady.xproject.main.mvp.contract.CommonRegisterContract;
import com.mcgrady.xskeleton.base.BaseModel;
import com.mcgrady.xskeleton.integration.IRepositoryManager;

public class CommonRegisterModel extends BaseModel implements CommonRegisterContract.Model {

    public CommonRegisterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}