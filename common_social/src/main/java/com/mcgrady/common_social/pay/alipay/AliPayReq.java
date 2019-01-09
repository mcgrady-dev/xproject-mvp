package com.mcgrady.common_social.pay.alipay;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.LogUtils;
import com.hjq.toast.ToastUtils;
import com.mcgrady.common_social.R;
import com.mcgrady.common_social.interf.OnPayListener;
import com.mcgrady.common_social.mvp.view.activity.H5PayDemoActivity;
import com.mcgrady.common_social.pay.PayAble;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/9
 */

public class AliPayReq extends PayAble {

    private Activity activity;

    private boolean resa2;

    private String appId;

    private RxPermissions rxPermissions;

    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "";

    /**
     *  pkcs8 格式的商户私钥。
     *
     * 	如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 	使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * 	RSA2_PRIVATE。
     *
     * 	建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 	工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    /**
     * 获取权限使用的 RequestCode
     */
    private static final int PERMISSIONS_REQUEST_CODE = 1002;

    private AliPayReq(Builder builder) {
        activity = builder.activity;
        resa2 = builder.resa2;
        appId = builder.appId;
        payListener = builder.payListener;
    }

    /**
     * 检查支付宝 SDK 所需的权限，并在必要的时候动态获取。
     * 在 targetSDK = 23 以上，READ_PHONE_STATE 和 WRITE_EXTERNAL_STORAGE 权限需要应用在运行时获取。
     * 如果接入支付宝 SDK 的应用 targetSdk 在 23 以下，可以省略这个步骤。
     */
    private void requestPermission() {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions((FragmentActivity) activity);
        }

        rxPermissions.requestEach(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {
                        // 用户允许权限
                    } else {
                        ToastUtils.show(activity.getString(R.string.permission_rejected));
                    }
                });
    }


    /**
     * 支付宝支付业务示例
     */
    @Override
    public void pay() {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(activity, activity.getString(R.string.error_missing_appid_rsa_private));
            return;
        }

        Observable.fromCallable(() -> new PayTask(activity)
        ).flatMap((Function<PayTask, ObservableSource<Map<String, String>>>) payTask -> {
            /**
             * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
             * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
             * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
             *
             * orderInfo 的获取必须来自服务端；
             */
            boolean rsa2 = (RSA2_PRIVATE.length() > 0);
            Map<String, String> params = OrderInfoUtil.buildOrderParamMap(APPID, rsa2);
            String orderParam = OrderInfoUtil.buildOrderParam(params);

            String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
            String sign = OrderInfoUtil.getSign(params, privateKey, rsa2);
            String orderInfo = orderParam + "&" + sign;

            return Observable.just(payTask.payV2(orderInfo, true));
        }).map(params -> {
            LogUtils.i(params.toString());
            return new PayResult(params);
        }).subscribe(payResult -> {
            /**
             * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                showAlert(activity, activity.getString(R.string.pay_success) + payResult);
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                showAlert(activity, activity.getString(R.string.pay_failed) + payResult);
            }
        });


    }


    /**
     * 支付宝账户授权业务示例
     */
    @Override
    public void auth() {
        if (TextUtils.isEmpty(PID)
                || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            showAlert(activity, activity.getString(R.string.error_auth_missing_partner_appid_rsa_private_target_id));
            return;
        }

        Observable.fromCallable(() -> new AuthTask(activity)
        ).flatMap((Function<AuthTask, ObservableSource<Map<String, String>>>) authTask -> {
            /**
             * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
             * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
             * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
             *
             * authInfo 的获取必须来自服务端；
             */
            boolean rsa2 = (RSA2_PRIVATE.length() > 0);
            Map<String, String> authInfoMap = OrderInfoUtil.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
            String info = OrderInfoUtil.buildOrderParam(authInfoMap);

            String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
            String sign = OrderInfoUtil.getSign(authInfoMap, privateKey, rsa2);
            final String authInfo = info + "&" + sign;

            return Observable.just(authTask.authV2(authInfo, true));
        }).map(params -> {
            LogUtils.i(params.toString());
            return new AuthResult(params, true);
        }).subscribe(authResult -> {
            String resultStatus = authResult.getResultStatus();

            // 判断resultStatus 为“9000”且result_code
            // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
            if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                // 获取alipay_open_id，调支付时作为参数extern_token 的value
                // 传入，则支付账户为该授权账户
                showAlert(activity, activity.getString(R.string.auth_success) + authResult);
            } else {
                // 其他状态值则为授权失败
                showAlert(activity, activity.getString(R.string.auth_failed) + authResult);
            }
        });
    }

    /**
     * 获取支付宝 SDK 版本号。
     */
    @Override
    public void showSdkVersion() {
        PayTask payTask = new PayTask(activity);
        String version = payTask.getVersion();
        showAlert(activity, activity.getString(R.string.alipay_sdk_version_is) + version);
    }

    /**
     * 将 H5 网页版支付转换成支付宝 App 支付的示例
     */
    @Override
    public void h5Pay() {
        Intent intent = new Intent(activity, H5PayDemoActivity.class);
        Bundle extras = new Bundle();

        /**
         * URL 是要测试的网站，在 Demo App 中会使用 H5PayDemoActivity 内的 WebView 打开。
         *
         * 可以填写任一支持支付宝支付的网站（如淘宝或一号店），在网站中下订单并唤起支付宝；
         * 或者直接填写由支付宝文档提供的“网站 Demo”生成的订单地址
         * （如 https://mclient.alipay.com/h5Continue.htm?h5_route_token=303ff0894cd4dccf591b089761dexxxx）
         * 进行测试。
         *
         * H5PayDemoActivity 中的 MyWebViewClient.shouldOverrideUrlLoading() 实现了拦截 URL 唤起支付宝，
         * 可以参考它实现自定义的 URL 拦截逻辑。
         */
        String url = "https://m.taobao.com";
        extras.putString("url", url);
        intent.putExtras(extras);
        activity.startActivity(intent);
    }

    public static final class Builder {
        private Activity activity;
        private boolean resa2;
        private String appId;
        private OnPayListener payListener;

        public Builder() {
        }

        public Builder with(Activity val) {
            activity = val;
            return this;
        }

        public Builder resa2(boolean val) {
            resa2 = val;
            return this;
        }

        public Builder appId(String val) {
            appId = val;
            return this;
        }

        public Builder addPayListener(OnPayListener val) {
            payListener = val;
            return this;
        }

        public AliPayReq build() {
            return new AliPayReq(this);
        }
    }
}
