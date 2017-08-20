package com.mcgrady.xproject.ui.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.mcgrady.xproject.R;
import com.mcgrady.xproject.base.BaseActivity;
import com.mcgrady.xproject.contract.MainContract;
import com.mcgrady.xproject.presenter.MainPresenter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    private TextView tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        LogUtils.d("test");

        Logger.addLogAdapter(new AndroidLogAdapter());

        Logger.d("debug");
        Logger.e("error");
        Logger.w("warning");
        Logger.v("verbose");
        Logger.i("information");
        Logger.wtf("wtf!!!!");

        Logger.clearLogAdapters();

        LogUtils.d("debug");
        LogUtils.e("error");
        LogUtils.w("warning");
        LogUtils.v("verbose");
        LogUtils.i("information");

        tvHello = (TextView) findViewById(R.id.tv_hello);

        tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void showTestDialog(String msg) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }
}
