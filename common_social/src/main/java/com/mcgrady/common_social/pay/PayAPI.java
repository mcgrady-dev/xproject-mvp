package com.mcgrady.common_social.pay;

import android.support.annotation.NonNull;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/9
 */

public class PayAPI {

    public static final Object mLock = new Object();
    private static PayAPI mInstance;

    public static PayAPI getInstance() {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new PayAPI();
                }
            }
        }

        return mInstance;
    }

    private PayAPI() {
    }


    public void pay(@NonNull PayAble payAble) {
        payAble.pay();
    }

    public void h5Pay(@NonNull PayAble payAble) {
        payAble.h5Pay();
    }

    public void auth(@NonNull PayAble payAble) {
        payAble.auth();
    }

    public void showSdkVersion(@NonNull PayAble payAble) {
        payAble.showSdkVersion();
    }
}
