package com.mcgrady.news.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jaeger.library.StatusBarUtil;
import com.mcgrady.common_core.base.BaseActivity;
import com.mcgrady.common_core.config.RouterHub;
import com.mcgrady.common_core.di.component.AppComponent;
import com.mcgrady.news.R;

@Route(path = RouterHub.ZHIHU_DAILY_DETAIL)
public class ZhihuDailyDetailActivity extends BaseActivity {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarUtil.setTransparent(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        StatusBarUtil.setTransparent(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.news_activity_zhihu_daily_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
