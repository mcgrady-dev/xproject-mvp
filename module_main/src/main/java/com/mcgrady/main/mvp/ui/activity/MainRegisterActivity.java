package com.mcgrady.main.mvp.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mcgrady.common_core.RouterHub;
import com.mcgrady.main.R;
import com.mcgrady.main.mvp.contract.CommonRegisterContract;
import com.mcgrady.main.mvp.presenter.CommonRegisterPresenter;
import com.mcgrady.xskeleton.base.BaseActivity;

@Route(path = RouterHub.MAIN_COMMON_REGISTER)
public class MainRegisterActivity extends BaseActivity<CommonRegisterPresenter> implements CommonRegisterContract.View {

    @Override
    public int getLayoutResId() {
        return R.layout.main_activity_register;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected CommonRegisterPresenter createPresenter() {
        return null;
    }
}
