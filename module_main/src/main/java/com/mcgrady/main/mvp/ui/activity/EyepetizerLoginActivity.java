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
    public int getLayoutResId() {
        return R.layout.main_activity_eyepetizer_login;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void navigation(String url) {

    }
}
