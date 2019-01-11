package com.mcgrady.common_social.pay.alipay;

import android.Manifest;
import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.hjq.toast.ToastUtils;
import com.mcgrady.common_social.interf.PayResultCallBack;
import com.mcgrady.common_social.pay.PayReqAble;
import com.mcgrady.common_social.util.AliUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * <p>支付宝支付</p>
 *
 * @author: mcgrady
 * @date: 2019/1/9
 */

public class AliPayReq extends PayReqAble {

    private RxPermissions rxPermissions;

    private PayTask payTask;
    private String params;
    private AliPayParam paramsModel;

    /**
     * 获取权限使用的 RequestCode
     */
    private static final int PERMISSIONS_REQUEST_CODE = 1002;

    private AliPayReq(Builder builder) {
        payResultCallBack = builder.payResultCallBack;
        rxPermissions = builder.rxPermissions;
        payTask = builder.payTask;
        params = builder.params;
        paramsModel = builder.paramsModel;
    }

    /**
     * 检查支付 SDK 所需的权限，并在必要的时候动态获取。
     * 在 targetSDK = 23 以上，READ_PHONE_STATE 和 WRITE_EXTERNAL_STORAGE 权限需要应用在运行时获取。
     * 如果接入支付宝 SDK 的应用 targetSdk 在 23 以下，可以省略这个步骤。
     */
    @SuppressLint("CheckResult")
    private void requestPermission(@NonNull CallBack callBack) {
        if (rxPermissions == null) {
            callBack.callback();
            return;
        }

        rxPermissions.requestEach(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {
                        // 用户允许权限
                        callBack.callback();
                    } else {
                        ToastUtils.show("无法获取支付宝 SDK 所需的权限, 请到系统设置开启");
                    }
                });
    }


    /**
     * 支付宝支付业务示例
     */
    @Override
    public void pay() {
        requestPermission(() -> {
            if (paramsModel == null) {
                doPay(params);
            } else {
                if (TextUtils.isEmpty(paramsModel.appId) || (TextUtils.isEmpty(paramsModel.rsa2_private)
                        && TextUtils.isEmpty(paramsModel.rsa_private))) {
                    ToastUtils.show("错误: 需要配置 APPID | RSA_PRIVATE");
                    return;
                }

                boolean rsa2 = (paramsModel.rsa2_private.length() > 0);
                //Map<String, String> params = OrderInfoUtil.buildOrderParamMap(appId, rsa2);
                Map<String, String> params = AliUtils.buildOrderParamMap(paramsModel.appId, rsa2,
                        paramsModel.biz_content, paramsModel.charset, paramsModel.method,
                        paramsModel.timestamp, paramsModel.version);

                String orderParam = AliUtils.buildOrderInfo(params);
                String privateKey = rsa2 ? paramsModel.rsa2_private : paramsModel.rsa_private;
                String sign = AliUtils.getSign(params, privateKey, rsa2);
                String orderInfo = orderParam + "&" + sign;

                doPay(orderInfo);
            }
        });
    }

    @SuppressLint("CheckResult")
    public void doPay(String orderParams) {
        if (TextUtils.isEmpty(orderParams)) {
            if (payResultCallBack != null) {
                payResultCallBack.onPayFailure("");
            }
            return;
        }

        Observable.fromCallable(new Callable<Object>() {
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

            }
        });

        Observable.fromCallable(() -> {
            return payTask.payV2(orderParams, true);
        }).map(stringMap -> {
            return new PayResult(stringMap);
        }).subscribe(payResult -> {

            /**
             * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                if (payResultCallBack != null) {
                    payResultCallBack.onPaySuccess(payResult.getResult());
                }
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                if (payResultCallBack != null) {
                    payResultCallBack.onPayFailure(payResult.getResult());
                }
            }
        });
    }

    public static final class Builder {
        private PayResultCallBack payResultCallBack;
        private RxPermissions rxPermissions;
        private PayTask payTask;
        private String params;
        private AliPayParam paramsModel;

        public Builder() {
        }

        public Builder payResultCallBack(PayResultCallBack val) {
            payResultCallBack = val;
            return this;
        }

        public Builder rxPermissions(RxPermissions val) {
            rxPermissions = val;
            return this;
        }

        public Builder with(PayTask val) {
            payTask = val;
            return this;
        }

        public Builder params(String val) {
            params = val;
            return this;
        }

        public Builder paramsModel(AliPayParam val) {
            paramsModel = val;
            return this;
        }

        public AliPayReq create() {
            return new AliPayReq(this);
        }
    }

    private interface CallBack {
        void callback();
    }
}
