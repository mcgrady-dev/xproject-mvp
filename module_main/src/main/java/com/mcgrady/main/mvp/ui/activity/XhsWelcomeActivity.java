package com.mcgrady.main.mvp.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.ConvertUtils;
import com.mcgrady.main.R;
import com.mcgrady.main.R2;
import com.mcgrady.main.mvp.ui.fragment.xhs.XhsLoginFragment;
import com.mcgrady.main.mvp.ui.fragment.xhs.XhsWelcomeFragment;
import com.mcgrady.main.widget.XhsParentViewPager;
import com.mcgrady.xskeleton.base.BaseActivity;
import com.mcgrady.xskeleton.di.component.AppComponent;

import butterknife.BindView;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/12/21
 */

public class XhsWelcomeActivity extends BaseActivity {

    /**
     * 点击返回键退出程序
     */
    private static Boolean isExit = false;
    private Handler mHandler = new Handler();

    private final int FRAGMENT_WELCOMEANIM = 0;
    private final int FRAGMENT_LOGINANIM = 1;

    @BindView(R2.id.main_iv_bg)
    ImageView ivBg;
    @BindView(R2.id.main_iv_logo)
    ImageView ivLogo;
    @BindView(R2.id.main_vp_parent)
    XhsParentViewPager vpParent;

    private float logoY;
    private AnimatorSet mAnimatorSet;

    private XhsLoginFragment loginFragment;
    private XhsWelcomeFragment welcomeFragment;

    @Override

    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.main_activity_xhs_welcome;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        vpParent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case FRAGMENT_WELCOMEANIM:
                        break;
                    case FRAGMENT_LOGINANIM:
                        XhsParentViewPager.mLoginPageLock = true;
                        ivLogo.postDelayed((Runnable) () -> {
                            if(logoY == 0){
                                //logoY = ViewHelper.getY(ivLogo);
                                logoY = ivLogo.getY();
                            }
                            playLogoInAnim();
                        },500);
                        vpParent.postDelayed(() -> loginFragment.playInAnim(), 300);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        vpParent.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                switch (i) {
                    case FRAGMENT_WELCOMEANIM:
                        welcomeFragment = new XhsWelcomeFragment();
                        vpParent.setWelcomAnimFragment(welcomeFragment);
                        welcomeFragment.setXhsWelcomeListener(() -> vpParent.setCurrentItem(1));
                        return welcomeFragment;
                    case FRAGMENT_LOGINANIM:
                        loginFragment = new XhsLoginFragment();
                        return loginFragment;
                    default:
                        break;
                }

                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
    }

    private void playLogoInAnim(){
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(ivLogo, "scaleX", 1.0f, 0.5f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(ivLogo, "scaleY", 1.0f, 0.5f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(ivLogo, "y", logoY, ConvertUtils.dp2px(15));

        if(mAnimatorSet != null && mAnimatorSet.isRunning()){
            mAnimatorSet.cancel();
            mAnimatorSet = null;
        }
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(anim1).with(anim2);
        mAnimatorSet.play(anim2).with(anim3);
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
            } else {
                finish();
                System.exit(0);
            }
        }
        return false;
    }
}
