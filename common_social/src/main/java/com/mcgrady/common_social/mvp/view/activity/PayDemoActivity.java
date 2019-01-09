package com.mcgrady.common_social.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mcgrady.common_core.base.BaseActivity;
import com.mcgrady.common_core.di.component.AppComponent;
import com.mcgrady.common_social.interf.OnPayListener;
import com.mcgrady.common_social.pay.alipay.AliPayReq;
import com.mcgrady.common_social.pay.PayAPI;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/9
 */

public class PayDemoActivity extends BaseActivity {


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        AliPayReq aliPayReq = new AliPayReq.Builder()
                .with(this)
                .appId("")
                .resa2(false)
                .addPayListener(new OnPayListener() {
                    @Override
                    public void onPaySuccess(String result) {

                    }

                    @Override
                    public void onPayFailure(String result) {

                    }

                    @Override
                    public void onPayConfirm(String result) {

                    }

                    @Override
                    public void onPayCheck(String status) {

                    }
                })
                .build();
        PayAPI.getInstance().pay(aliPayReq);
    }
}
