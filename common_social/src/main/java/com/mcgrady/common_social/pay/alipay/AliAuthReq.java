package com.mcgrady.common_social.pay.alipay;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.alipay.sdk.app.AuthTask;
import com.hjq.toast.ToastUtils;
import com.mcgrady.common_social.interf.AuthResultCallBack;
import com.mcgrady.common_social.util.AliUtils;

import java.util.Map;

import io.reactivex.Observable;

/**
 * <p>支付宝授权请求</p>
 *
 * @author: mcgrady
 * @date: 2019/1/11
 */

public class AliAuthReq {

    private AuthTask authTask;
    private String params;
    private AliPayParam paramsModel;
    private AuthResultCallBack authResultCallBack;

    private AliAuthReq(Builder builder) {
        authTask = builder.authTask;
        params = builder.params;
        paramsModel = builder.paramsModel;
        authResultCallBack = builder.authResultCallBack;
    }

    /**
     * 支付宝账户授权业务示例
     */
    public void auth() {
        if (paramsModel == null) {
            doAuth(params);
        } else {

            if (TextUtils.isEmpty(paramsModel.pId)
                    || TextUtils.isEmpty(paramsModel.appId)
                    || (TextUtils.isEmpty(paramsModel.rsa2_private) && TextUtils.isEmpty(paramsModel.rsa_private))
                    || TextUtils.isEmpty(paramsModel.targetId)) {
                ToastUtils.show("错误: 需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID");
                return;
            }

            /**
             * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
             * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
             * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
             *
             * authInfo 的获取必须来自服务端；
             */
            boolean rsa2 = (paramsModel.rsa2_private.length() > 0);
            Map<String, String> authInfoMap = AliUtils.buildAuthInfoMap(paramsModel.pId,
                    paramsModel.appId, paramsModel.targetId, rsa2);
            String info = AliUtils.buildOrderInfo(authInfoMap);

            String privateKey = rsa2 ? paramsModel.rsa2_private : paramsModel.rsa_private;
            String sign = AliUtils.getSign(authInfoMap, privateKey, rsa2);
            String authInfo = info + "&" + sign;

            doAuth(authInfo);
        }
    }

    @SuppressLint("CheckResult")
    private void doAuth(String authInfo) {
        if (TextUtils.isEmpty(authInfo)) {
            if (authResultCallBack != null) {
                ToastUtils.show("错误: 需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID");
                authResultCallBack.onAuthFailure("");
            }

            return;
        }

        Observable.fromCallable(() -> {
            return authTask.authV2(authInfo, true);
        }).map(stringMap -> {
            return new AuthResult(stringMap, true);
        }).subscribe(authResult -> {
            String resultStatus = authResult.getResultStatus();

            // 判断resultStatus 为“9000”且result_code
            // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
            if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                // 获取alipay_open_id，调支付时作为参数extern_token 的value
                // 传入，则支付账户为该授权账户
                if (authResultCallBack != null) {
                    authResultCallBack.onAuthSuccess(authResult.getResult());
                }
            } else {
                // 其他状态值则为授权失败
                if (authResultCallBack != null) {
                    authResultCallBack.onAuthFailure(authResult.getResult());
                }
            }
        });
    }


    public static final class Builder {
        private AuthTask authTask;
        private String params;
        private AliPayParam paramsModel;
        private AuthResultCallBack authResultCallBack;

        public Builder() {
        }

        public Builder authTask(AuthTask val) {
            authTask = val;
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

        public Builder authResultCallBack(AuthResultCallBack val) {
            authResultCallBack = val;
            return this;
        }

        public AliAuthReq build() {
            return new AliAuthReq(this);
        }
    }
}
