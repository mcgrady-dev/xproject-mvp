package com.mcgrady.main.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.mcgrady.common_core.RouterHub;
import com.mcgrady.common_res.widget.ClearEditText;
import com.mcgrady.main.R;
import com.mcgrady.main.R2;
import com.mcgrady.main.mvp.contract.CommonLoginContract;
import com.mcgrady.main.mvp.model.CommonLoginModel;
import com.mcgrady.main.mvp.presenter.CommonLoginPresenter;
import com.mcgrady.common_res.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RouterHub.MAIN_COMMON_LOGIN)
public class MainLoginActivity extends BaseActivity<CommonLoginPresenter> implements CommonLoginContract.View {

    @BindView(R2.id.main_edt_login_phone)
    ClearEditText edtMobile;
    @BindView(R2.id.main_edt_login_password)
    ClearEditText edtPassword;

    @Override
    public int getLayoutResId() {
        return R.layout.main_activity_login;
    }

    @Override
    protected CommonLoginPresenter createPresenter() {
        return new CommonLoginPresenter(new CommonLoginModel(), this);
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
    }

    @OnClick({R2.id.main_tv_login_register, R2.id.main_btn_login_commit})
    void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.main_btn_login_commit) {
            presenter.loginByMobile(edtMobile.getText().toString(), edtPassword.getText().toString());

        } else if (viewId == R.id.main_tv_login_register) {
            ARouter.getInstance()
                    .build(RouterHub.MAIN_COMMON_REGISTER)
                    .navigation();

        }
    }
}
