package com.mcgrady.laboratory;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mcgrady.core.base.BaseActivity;

@Route(path = "/laboratory/MainActivity")
public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
    }

    @Override
    public void initInject(Bundle savedInstanceState) {

    }
}
