package com.mcgrady.main.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.mcgrady.common_core.RouterHub;
import com.mcgrady.main.R;
import com.mcgrady.main.R2;
import com.mcgrady.common_res.base.BaseActivity;
import com.mcgrady.xskeleton.base.IPresenter;

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
    @BindView(R2.id.main_iv_logo)
    ImageView ivLogo;
    @BindView(R2.id.main_iv_splash_shadow)
    ImageView ivSplashShadow;

    private Animation splashAnimation;

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.FullscreenTheme);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean immersionBarEnabled() {
        return false;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.main_activity_zhihu_daily_splash;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        splashAnimation = AnimationUtils.loadAnimation(this, R.anim.public_splash);

        splashAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                ivSplash.setVisibility(View.GONE);
                ivSplashShadow.setVisibility(View.GONE);
                ivLogo.setVisibility(View.GONE);

                ARouter.getInstance().build(RouterHub.ZHIHU_DAILY_HOME)
                        .navigation(ZhihuDailySplashActivity.this);
                finish();
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


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onBackPressed() {
        //不执行回退操作
    }
}
