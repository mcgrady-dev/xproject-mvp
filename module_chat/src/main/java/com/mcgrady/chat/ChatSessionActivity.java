package com.mcgrady.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mcgrady.common_core.base.BaseActivity;
import com.mcgrady.common_core.di.component.AppComponent;

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
}
