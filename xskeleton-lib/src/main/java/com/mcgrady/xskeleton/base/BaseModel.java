package com.mcgrady.xskeleton.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.mcgrady.xskeleton.integration.IRepositoryManager;

/**
 * Created by mcgrady on 2019-08-10.
 */
public class BaseModel implements IModel, LifecycleObserver {

    protected IRepositoryManager repositoryManager;    //用于管理网络请求层, 以及数据缓存层

    public BaseModel(IRepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    @Override
    public void onDestroy() {
        repositoryManager = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
    }
}
