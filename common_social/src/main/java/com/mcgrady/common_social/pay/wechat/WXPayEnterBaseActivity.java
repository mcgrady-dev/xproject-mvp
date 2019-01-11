package com.mcgrady.common_social.pay.wechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.mcgrady.common_social.pay.PayAPI;
import com.mcgrady.common_social.pay.PayReqAble;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/10
 */

public class WXPayEnterBaseActivity  extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PayReqAble payReqAble = PayAPI.getInstance().getPayReqAble();
        if (payReqAble != null && payReqAble instanceof WechatPayReq) {
            ((WechatPayReq) payReqAble).handleIntent(getIntent(), this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        PayReqAble payReqAble = PayAPI.getInstance().getPayReqAble();
        if (payReqAble != null && payReqAble instanceof WechatPayReq) {
            ((WechatPayReq) payReqAble).handleIntent(getIntent(), this);
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
        LogUtils.i("onReq===>>> baseReq.getType : " + baseReq.getType());
    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtils.i("onResp===>>> resp.getType : "+ resp.getType());

        /**
         * 0    成功 展示成功页面
         * -1   错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
         * -2   用户取消 无需处理。发生场景：用户不支付了，点击取消，返回APP。
         */
        switch (resp.getType()) {
            case ConstantsAPI.COMMAND_PAY_BY_WX:
                PayReqAble payReqAble = PayAPI.getInstance().getPayReqAble();
                if (payReqAble != null && payReqAble instanceof WechatPayReq) {
                    ((WechatPayReq) payReqAble).onResp(resp.errCode);
                }
                break;
            default:
                break;
        }
    }
}
