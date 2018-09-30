package com.mcgrady.test;

import android.os.Bundle;

import com.mcgrady.core.base.BaseActivity;
import com.mcgrady.core.base.Navigation;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/9/20
 */

public class LauncherActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

        new Navigation().startActivity(this, "/main/SplashFragment", new Bundle());
    }
}
