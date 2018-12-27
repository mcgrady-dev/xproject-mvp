package com.mcgrady.main.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.mcgrady.main.R;
import com.mcgrady.main.mvp.ui.fragment.xhs.XhsLoginFragment;
import com.mcgrady.main.mvp.ui.fragment.xhs.XhsWelcomeFragment;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/12/21
 */

public class XhsWelcomeActivity extends BaseActivity {

    private XhsLoginFragment loginFragment;
    private XhsWelcomeFragment welcomeFragment;

    @Override

    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.main_activity_xhs_welcome;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
