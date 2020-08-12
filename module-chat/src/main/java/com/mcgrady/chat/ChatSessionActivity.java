package com.mcgrady.chat;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mcgrady.common_core.base.BaseActivity;
import com.mcgrady.xskeleton.base.IPresenter;


public class ChatSessionActivity extends BaseActivity {

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.chat_activity_session;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
