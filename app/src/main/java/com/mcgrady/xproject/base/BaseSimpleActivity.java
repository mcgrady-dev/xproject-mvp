package com.mcgrady.xproject.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.mcgrady.xproject.app.ActivityStack;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by mcgrady on 2017/7/27.
 * Activity碎片基类, 基于fragmentation封装
 */

public abstract class BaseSimpleActivity extends SupportActivity {

    protected Activity mContext;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        onViewCreated();
        ActivityStack.getInstance().pushActivity(this);
        init();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            ActivityStack.getInstance().popActivity(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getInstance().popActivity(this);
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    protected void onViewCreated() {

    }

    protected void init() {

    }

    @LayoutRes
    protected abstract int getLayoutId();
}
