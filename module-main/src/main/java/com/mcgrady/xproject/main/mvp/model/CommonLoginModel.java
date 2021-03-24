package com.mcgrady.xproject.main.mvp.model;

import com.mcgrady.xproject.main.mvp.contract.CommonLoginContract;
import com.mcgrady.xskeleton.base.BaseModel;
import com.mcgrady.xskeleton.integration.IRepositoryManager;
import com.mcgrady.xskeleton.utils.XUtils;

public class CommonLoginModel extends BaseModel implements CommonLoginContract.Model {

    public CommonLoginModel() {
        this(XUtils.obtainAppComponentFromContext().repositoryManager());
    }

    public CommonLoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}