package com.mcgrady.main.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mcgrady.common_core.base.BaseActivity;
import com.mcgrady.common_core.di.component.AppComponent;
import com.mcgrady.main.R;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/3/4
 */

public class ZhihuDailySplashActivity extends BaseActivity {

    private Animation splashAnimation;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.main_activity_zhihu_daily_splash;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        splashAnimation = AnimationUtils.loadAnimation(this, R.anim.public_splash);

    }
}
