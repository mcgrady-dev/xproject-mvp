package com.mcgrady.main.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.mcgrady.common_core.RouterHub;
import com.mcgrady.main.R;
import com.mcgrady.main.R2;
import com.mcgrady.xskeleton.base.BaseActivity;
import com.mcgrady.xskeleton.di.component.AppComponent;

import butterknife.BindView;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/3/4
 */
@Route(path = RouterHub.ZHIHU_DAILY_SPLASH)
public class ZhihuDailySplashActivity extends BaseActivity {

    @BindView(R2.id.main_iv_splash)
    ImageView ivSplash;

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

        splashAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                ivSplash.setVisibility(View.GONE);
                ARouter.getInstance().build(RouterHub.ZHIHU_DAILY_HOME)
                        .navigation(ZhihuDailySplashActivity.this);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ivSplash.post(new Runnable() {
            @Override
            public void run() {
                ivSplash.startAnimation(splashAnimation);
            }
        });
    }
}
