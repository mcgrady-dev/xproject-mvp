package com.mcgrady.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mcgrady.xskeleton.base.BaseActivity;
import com.mcgrady.xskeleton.di.component.AppComponent;


public class ChatSessionActivity extends BaseActivity {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.chat_activity_session;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public boolean useFragment() {
        return false;
    }
}
