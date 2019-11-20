package com.mcgrady.xskeleton.http.interf

import io.reactivex.disposables.Disposable

/**
 * Created by mcgrady on 2019-07-29.
 */
interface ResponseCallback<T> {
    /**
     * 请求成功
     *
     * @param resultCode
     * @param data
     */
    fun onSuccess(resultCode: Int, data: T)

    fun onFailure(statusCode: Int, errorMsg: String?) { //LogUtils.d("onFailure: statusCode=" + statusCode + " errroMsg=" + errorMsg);
    }

    /**
     * 请求结束
     */
    fun onFinish() { //LogUtils.d("onFinish:");
    }

    fun onSubscribe(d: Disposable?) {}
}