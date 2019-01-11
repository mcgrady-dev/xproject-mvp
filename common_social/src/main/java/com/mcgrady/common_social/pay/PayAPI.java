package com.mcgrady.common_social.pay;

import android.support.annotation.NonNull;

/**
 * <p>支付包装类</p>
 *
 * @author: mcgrady
 * @date: 2019/1/9
 */

public class PayAPI {

    public static final Object mLock = new Object();
    private static PayAPI instance;
    private PayReqAble payReqAble;

    public static PayAPI getInstance() {
        if (instance == null) {
            synchronized (mLock) {
                if (instance == null) {
                    instance = new PayAPI();
                }
            }
        }

        return instance;
    }

    private PayAPI() {
    }

    public void pay(@NonNull PayReqAble payReqAble) {
        this.payReqAble = payReqAble;
        this.payReqAble.pay();
    }

    public PayReqAble getPayReqAble() {
        return payReqAble;
    }
}
