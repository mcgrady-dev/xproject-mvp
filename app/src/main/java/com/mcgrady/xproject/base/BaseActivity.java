package com.mcgrady.xproject.base;


import com.mcgrady.xproject.app.App;
import com.mcgrady.xproject.di.component.ActivityComponent;
import com.mcgrady.xproject.di.component.DaggerActivityComponent;
import com.mcgrady.xproject.di.module.ActivityModule;

import javax.inject.Inject;

/**
 * Created by mcgrady on 2017/7/27.
 * Activity基类（MVP）
 */

public abstract class BaseActivity<T extends BasePresenter> extends BaseAppCompatActivity implements BaseView {

    @Inject
    protected T mPresenter;

    protected ActivityComponent getActivityComponent() {
        return  DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule(){
        return new ActivityModule(this);
    }


    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showErrorMsg(String msg) {
        // ...
    }

    @Override
    public void stateError() {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateMain() {

    }

    protected abstract void initInject();
}
