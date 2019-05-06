package com.mcgrady.main.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mcgrady.main.R;
import com.mcgrady.main.di.component.DaggerMainRegisterComponent;
import com.mcgrady.main.mvp.contract.MainRegisterContract;
import com.mcgrady.main.mvp.presenter.MainRegisterPresenter;
import com.mcgrady.xskeleton.base.BaseActivity;
import com.mcgrady.xskeleton.di.component.AppComponent;

public class MainRegisterActivity extends BaseActivity<MainRegisterPresenter> implements MainRegisterContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainRegisterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.main_activity_register;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
