package com.mcgrady.xproject.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by mcgrady on 2017/7/27.
 * Activity碎片基类
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {

    protected Activity mContext;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        onViewCreated();
//        App.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();

    }

    protected void onViewCreated() {

    }

    @LayoutRes
    protected abstract int getLayoutId();
}
