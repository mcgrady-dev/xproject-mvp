package com.mcgrady.main.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.rxbus.RxBus;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mcgrady.core.Constants;
import com.mcgrady.core.base.BasePresenterActivity;
import com.mcgrady.core.utils.MultiClickUtil;
import com.mcgrady.main.R;
import com.mcgrady.main.event.LoadMainBottomNavigationEvent;
import com.mcgrady.main.ui.fragment.BottomNavigationFragment;
import com.mcgrady.main.ui.fragment.SplashFragment;


/**
 * <p>主页面，包含启动页</p>
 *
 * @author: mcgrady
 * @date: 2018/5/16
 */
@Route(path = "/main/MainActivity")
public class MainActivity extends BasePresenterActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        BarUtils.setStatusBarAlpha(this, 0);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_main;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        String version = SPUtils.getInstance(Constants.SP_FILE_NAME_VERSION).getString(Constants.VERSION);
        if (version.equals(AppUtils.getAppVersionName())) {
            loadRootFragment(R.id.fl_container, savedInstanceState == null ?
                    BottomNavigationFragment.newInstance() : findFragment(BottomNavigationFragment.class));
        } else {
            loadRootFragment(R.id.fl_container, savedInstanceState == null ?
                    SplashFragment.newInstance() : findFragment(SplashFragment.class));
        }

        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                super.onFragmentCreated(fm, f, savedInstanceState);
                LogUtils.i(TAG, "onFragmentCreated-->" + f.getClass().getSimpleName());
            }

            @Override
            public void onFragmentStopped(FragmentManager fm, Fragment f) {
                super.onFragmentStopped(fm, f);
                LogUtils.i(TAG, "onFragmentStopped-->" + f.getClass().getSimpleName());
            }
        }, true);

        RxBus.getDefault().subscribe(this, new RxBus.Callback<LoadMainBottomNavigationEvent>() {
            @Override
            public void onEvent(LoadMainBottomNavigationEvent event) {
                loadRootFragment(R.id.fl_container, BottomNavigationFragment.newInstance());
            }
        });
    }

    @Override
    public void initInject(Bundle savedInstanceState) {
    }

    @Override
    public void onBackPressedSupport() {
        if (MultiClickUtil.isMultiClick(2000L)) {
            ActivityCompat.finishAfterTransition(this);
        } else {
            ToastUtils.showShort("再点一次退出");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(this);
    }
}
