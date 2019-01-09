package com.mcgrady.common_social.pay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.mcgrady.common_social.R;
import com.mcgrady.common_social.interf.OnPayListener;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/9
 */

public abstract class PayAble {

    protected OnPayListener payListener;

    /**
     * 授权
     */
    protected abstract void auth();

    /**
     * 支付
     */
    protected abstract void pay();

    /**
     * 网页支付转 native 支付
     */
    protected abstract void h5Pay();

    /**
     * 获取sdk版本号
     */
    protected abstract void showSdkVersion();

    protected void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    protected void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }
}
