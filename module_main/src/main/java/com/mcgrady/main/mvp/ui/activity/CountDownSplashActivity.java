package com.mcgrady.main.mvp.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.mcgrady.main.R;
import com.mcgrady.main.R2;
import com.mcgrady.xskeleton.base.BaseActivity;
import com.mcgrady.xskeleton.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 倒计时欢迎页
 *
 * @author: mcgrady
 * @date: 2019-06-10
 */
public class CountDownSplashActivity extends BaseActivity {

    @BindView(R2.id.btn_skip_splash)
    Button btnSkip;

    private CountDownTimer countDownTimer;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public int getLayoutResId() {
        return R.layout.main_activity_countdown_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initImmersionBar() {
        //不执行状态栏适配
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        countDownTimer = new CountDownTimer(3200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnSkip.setText("跳过(" + millisUntilFinished / 1000 + "s)");
            }

            @Override
            public void onFinish() {
                toActivity();
            }
        };

        startClock();
    }

    @OnClick({R2.id.btn_skip_splash})
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_skip_splash) {
            toActivity();
        }
    }

    private void startClock() {
        btnSkip.setVisibility(View.VISIBLE);
        countDownTimer.start();
    }

    private void toActivity() {
        btnSkip.setText("跳过(" + 0 + "s)");
        countDownTimer.cancel();
        //todo local token is null go to LoginActivity , else go to MainActivity
        ActivityUtils.startActivity(MainLoginActivity.class);
        finish();
    }

    @Override
    public void onBackPressed() {
        //不执行回退操作
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
