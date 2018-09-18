package com.mcgrady.main.net;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.mcgrady.core.base.BaseSubscriber;
import com.mcgrady.core.http.SubscriberCallback;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/9/18
 */

public class SplashSubscriber<T> extends BaseSubscriber<T> {

    public SplashSubscriber(SubscriberCallback subscriberCallback, Context context) {
        super(subscriberCallback, context);
    }

    public SplashSubscriber(SubscriberCallback subscriberCallback) {
        super(subscriberCallback);
    }

    @Override
    public void onNext(T response) {
        if (subscriberCallback != null) {
            if (response != null) {
                subscriberCallback.onSuccess(response);
            } else {
//                subscriberCallback.onFail();
                LogUtils.e("error");
            }

        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
    }
}
