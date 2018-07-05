package com.mcgrady.core.base;

import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.mcgrady.core.http.ApiResponse;
import com.mcgrady.core.http.HttpExceptionHandler;
import com.mcgrady.core.http.ProgressCancelListener;
import com.mcgrady.core.http.SubscriberListener;
import com.mcgrady.core.http.params.HttpErrorResponse;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/6/21
 */

public class BaseSubscriber<T extends ApiResponse> extends ResourceSubscriber<T> implements ProgressCancelListener {

    private static final String TAG = BaseSubscriber.class.getSimpleName();
    protected SubscriberListener subscriberListener;
    private Context context;

    /**
     * 该构造会出现一个自动弹出和消失的dialog,一般使用与通用情况,特殊情况请自行处理,也可以通过{@link SubscriberListener#isShowLoading()}
     *
     * @param subscriberListener
     * @param context
     */
    public BaseSubscriber(SubscriberListener subscriberListener, Context context) {
        this.subscriberListener = subscriberListener;
        this.context = context;
    }

    /**
     * 使用该构造方法没有LoadingDialog
     *
     * @param subscriberListener
     */
    public BaseSubscriber(SubscriberListener subscriberListener) {
        this.subscriberListener = subscriberListener;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (subscriberListener != null && subscriberListener.isShowLoading()) {
            onBegin();
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    public void onBegin() {
        Log.i(TAG, "onBegin");
        if (subscriberListener != null) {
            subscriberListener.onBegin();
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
        if (subscriberListener != null) {
            subscriberListener.onError(e);
        }
        onComplete();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        LogUtils.i(TAG, "onCompleted");
        if (subscriberListener != null && subscriberListener.isShowLoading()) {
//            dismissProgressDialog();
            if (subscriberListener != null) {
                subscriberListener.onCompleted();
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
        if (subscriberListener != null) {
            if (response!=null) {
                subscriberListener.onSuccess(response);
            }
            else {
                subscriberListener.onFail(new HttpErrorResponse("数据为空", HttpExceptionHandler.ERROR.NO_DATA_ERROR));

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
