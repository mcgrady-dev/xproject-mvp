package com.mcgrady.xproject.base;


import com.mcgrady.xproject.app.App;
import com.mcgrady.xproject.di.component.ActivityComponent;
import com.mcgrady.xproject.di.component.DaggerActivityComponent;
import com.mcgrady.xproject.di.module.ActivityModule;

import javax.inject.Inject;

/**
 * Created by mcgrady on 2017/7/27.
 * MVP Activity基类
 */

public abstract class BaseActivity<T extends BasePresenter> extends BaseSimpleActivity implements BaseView {

    @Inject
    protected T mPresenter;

    /**
     * 上一次点击时间
     */
    private long lastClick = 0;

    protected ActivityComponent getActivityComponent() {
        return  DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule(){
        return new ActivityModule(this);
    }

    /**
     * 判断是否快速点击
     * @return
     */
    protected boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }

        return true;
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
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
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
