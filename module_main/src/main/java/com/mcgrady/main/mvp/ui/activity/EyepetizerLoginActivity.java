package com.mcgrady.main.mvp.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mcgrady.main.R;
import com.mcgrady.main.mvp.contract.CommonLoginContract;
import com.mcgrady.main.mvp.presenter.CommonLoginPresenter;
import com.mcgrady.common_res.base.BaseActivity;

public class EyepetizerLoginActivity extends BaseActivity<CommonLoginPresenter> implements CommonLoginContract.View {

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
    protected CommonLoginPresenter createPresenter() {
        return null;
    }
}
