package com.mcgrady.xskeleton.http.interf;


import io.reactivex.disposables.Disposable;

/**
 * Created by mcgrady on 2019-07-29.
 */
public interface ResponseCallback<T> {

    /**
     * 请求成功
     *
     * @param resultCode
     * @param data
     */
    void onSuccess(int resultCode, T data);

    default void onFailure(int statusCode, String errorMsg) {
        //LogUtils.d("onFailure: statusCode=" + statusCode + " errroMsg=" + errorMsg);
    }

    /**
     * 请求结束
     */
    default void onFinish() {
        //LogUtils.d("onFinish:");
    }

    default void onSubscribe(Disposable d) {
    }
}
