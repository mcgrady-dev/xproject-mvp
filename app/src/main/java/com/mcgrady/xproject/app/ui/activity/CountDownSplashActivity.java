package com.mcgrady.xproject.app.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.mcgrady.common_core.RouterHub;
import com.mcgrady.common_core.base.BaseActivity;
import com.mcgrady.xproject.app.R;
import com.mcgrady.xskeleton.base.IPresenter;

import butterknife.BindView;

/**
 * 倒计时欢迎页
 *
 * @author: mcgrady
 * @date: 2019-06-10
 */
public class CountDownSplashActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.btn_skip_splash)
    Button btnSkip;

    private CountDownTimer countDownTimer;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_countdown_splash;
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean immersionBarEnabled() {
        return false;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        btnSkip = findViewById(R.id.btn_skip_splash);
        btnSkip.setOnClickListener(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        countDownTimer = new CountDownTimer(3000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                btnSkip.setText("跳过(" + millisUntilFinished / 1000 + "s)");
            }

            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toActivity();
                    }
                });
            }
        };

        startClock();
    }

    //@OnClick({R2.id.btn_skip_splash})
    @Override
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

    @SuppressLint("SetTextI18n")
    private void toActivity() {
        btnSkip.setText("跳过(" + 0 + "s)");
        countDownTimer.cancel();
        //todo local token is null go to LoginActivity , else go to MainActivity
//        ActivityUtils.startActivity("com.mcgrady.xproject.main", "MainLoginActivity");

        ARouter.getInstance().build(RouterHub.MAIN_COMMON_LOGIN).navigation();
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
