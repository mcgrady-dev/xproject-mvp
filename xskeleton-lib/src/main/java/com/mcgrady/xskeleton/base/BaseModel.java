package com.mcgrady.xskeleton.base;

import com.google.gson.Gson;
import com.mcgrady.xskeleton.http.handler.RxErrorHandler;
import com.mcgrady.xskeleton.integration.IRepositoryManager;
import com.mcgrady.xskeleton.integration.RepositoryManager;

/**
 * Created by mcgrady on 2019-08-10.
 */
public class BaseModel implements IModel {

    protected IRepositoryManager mRepositoryManager;
    protected Gson mGson;

    public BaseModel() {
        this(RepositoryManager.getInstance());
    }

    public BaseModel(IRepositoryManager mRepositoryManager) {
        this.mRepositoryManager = mRepositoryManager;
        this.mGson = new Gson();
    }

    public RxErrorHandler getRxErrorHandler() {
        return mRepositoryManager.getRxErrorHandler();
    }

    @Override
    public void onDestroy() {
        mGson = null;
    }
}
