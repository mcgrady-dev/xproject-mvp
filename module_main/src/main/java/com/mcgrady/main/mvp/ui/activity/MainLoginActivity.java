package com.mcgrady.main.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.mcgrady.common_core.RouterHub;
import com.mcgrady.common_res.widget.ClearEditText;
import com.mcgrady.main.R;
import com.mcgrady.main.R2;
import com.mcgrady.main.di.component.DaggerCommonLoginComponent;
import com.mcgrady.main.mvp.contract.CommonLoginContract;
import com.mcgrady.main.mvp.presenter.CommonLoginPresenter;
import com.mcgrady.xskeleton.base.BaseActivity;
import com.mcgrady.xskeleton.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RouterHub.MAIN_COMMON_LOGIN)
public class MainLoginActivity extends BaseActivity<CommonLoginPresenter> implements CommonLoginContract.View {

    @BindView(R2.id.main_edt_login_phone)
    ClearEditText edtMobile;
    @BindView(R2.id.main_edt_login_password)
    ClearEditText edtPassword;

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
        return R.layout.main_activity_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
    }

    @OnClick({R2.id.main_tv_login_register, R2.id.main_btn_login_commit})
    void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.main_btn_login_commit) {
            mPresenter.loginByMobile(edtMobile.getText().toString(), edtPassword.getText().toString());

        } else if (viewId == R.id.main_tv_login_register) {
            ARouter.getInstance()
                    .build(RouterHub.MAIN_COMMON_REGISTER)
                    .navigation(MainLoginActivity.this);

        }
    }

    @Override
    public void navigation(String url) {
        ARouter.getInstance().build(url).navigation(MainLoginActivity.this);
    }
}
