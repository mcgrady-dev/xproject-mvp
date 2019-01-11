package com.mcgrady.common_social.pay.alipay;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alipay.sdk.app.PayTask;
import com.mcgrady.common_core.base.BaseActivity;
import com.mcgrady.common_core.di.component.AppComponent;
import com.mcgrady.common_social.interf.PayResultCallBack;
import com.mcgrady.common_social.pay.PayAPI;
import com.mcgrady.common_social.pay.wechat.WechatPayReq;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

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
                .with(new PayTask(PayDemoActivity.this))
                .payResultCallBack(new PayResultCallBack() {
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
                .create();

        PayAPI.getInstance().pay(aliPayReq);

        WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                .with(WXAPIFactory.createWXAPI(PayDemoActivity.this, null))
                .appId("")
                .partnerId("")
                .prepayId("")
                .nonceStr("")
                .timeStamp("")
                .sign("")
                .payCallback(new PayResultCallBack() {
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
                .create();

        PayAPI.getInstance().pay(wechatPayReq);
    }
}
