package com.mcgrady.xproject.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mcgrady.xproject.app.App;
import com.mcgrady.xproject.di.component.DaggerFragmentComponent;
import com.mcgrady.xproject.di.component.FragmentComponent;
import com.mcgrady.xproject.di.module.FragmentModule;

import javax.inject.Inject;

/**
 * Created by mcgrady on 2017/8/29.
 * MVP Fragment基类
 */

public abstract class BaseFragment<T extends BasePresenter> extends BaseFragmentImpl implements BaseView {

    @Inject
    protected T mPresenter;

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule() { return new FragmentModule(this); }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initInject();
        mPresenter.attachView(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void showErrorMsg(String msg) {
    }

    @Override
    public void stateError() {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateMain() {

    }

    protected abstract void initInject();

}
