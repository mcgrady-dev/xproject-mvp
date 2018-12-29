package com.mcgrady.core.base;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.mcgrady.core.http.HttpErrorException;
import com.mcgrady.core.http.ProgressCancelListener;
import com.mcgrady.core.http.SubscriberCallback;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * <p>在Http请求结束时，关闭ProgressDialog
 * 调用者自己对请求数据进行处理</p>
 *
 * @author: mcgrady
 * @date: 2018/6/21
 */

public class BaseSubscriber<T> extends ResourceSubscriber<T> implements ProgressCancelListener {

    private static final String TAG = BaseSubscriber.class.getSimpleName();
    protected SubscriberCallback subscriberCallback;
    private Context mContext;

    /**
     * 该构造会出现一个自动弹出和消失的dialog,一般使用与通用情况,特殊情况请自行处理,也可以通过{@link SubscriberCallback#isShowLoading()}
     *
     * @param subscriberCallback
     * @param context
     */
    public BaseSubscriber(SubscriberCallback subscriberCallback, Context context) {
        this.subscriberCallback = subscriberCallback;
        this.mContext = context;
    }

    /**
     * 使用该构造方法没有LoadingDialog
     *
     * @param subscriberCallback
     */
    public BaseSubscriber(SubscriberCallback subscriberCallback) {
        this.subscriberCallback = subscriberCallback;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (subscriberCallback != null && subscriberCallback.isShowLoading()) {
            onBegin();
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    public void onBegin() {
        LogUtils.i(TAG, "onBegin");
        if (subscriberCallback != null) {
            subscriberCallback.onBegin();
        }
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        LogUtils.i(TAG, "onError:" + e.toString());
        if (subscriberCallback != null) {
            subscriberCallback.onError(e);
        }
        onComplete();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        LogUtils.i(TAG, "onCompleted");
        if (subscriberCallback != null && subscriberCallback.isShowLoading()) {
//            dismissProgressDialog();
            if (subscriberCallback != null) {
                subscriberCallback.onCompleted();
            }
        }
        if (!this.isDisposed()) {
            this.dispose();
        }
    }


    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理,可以根据实际情况再封装
     *
     * @param response 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T response) {
        LogUtils.i(TAG, "onNext");
        if (subscriberCallback != null) {
            if (response!=null) {
                subscriberCallback.onSuccess(response);
            }
            else {
                subscriberCallback.onFail(new HttpErrorException("数据为空", HttpErrorException.NO_DATA_ERROR));
            }
        }
    }

    @Override
    public void onCancelProgress() {
        if (isDisposed()) {
            this.dispose();
        }
    }
}
