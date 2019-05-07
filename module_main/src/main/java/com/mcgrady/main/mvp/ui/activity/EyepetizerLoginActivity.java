package com.mcgrady.main.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mcgrady.main.R;
import com.mcgrady.main.di.component.DaggerCommonLoginComponent;
import com.mcgrady.main.mvp.contract.CommonLoginContract;
import com.mcgrady.main.mvp.presenter.CommonLoginPresenter;
import com.mcgrady.xskeleton.base.BaseActivity;
import com.mcgrady.xskeleton.di.component.AppComponent;

public class EyepetizerLoginActivity extends BaseActivity<CommonLoginPresenter> implements CommonLoginContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommonLoginComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.main_activity_eyepetizer_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void navigation(String url) {

    }
}
