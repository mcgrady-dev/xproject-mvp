package com.mcgrady.xskeleton.http.handler

import android.text.TextUtils
import com.hjq.toast.ToastUtils
import com.mcgrady.xskeleton.http.interf.IBaseResponse
import com.mcgrady.xskeleton.http.interf.ResponseCallback
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by mcgrady on 2019-08-22.
 */
class ErrorHandlerObserver<T> : Observer<T?> {
    private var errorHandlerFactory: ErrorHandlerFactory?
    private var callback: ResponseCallback<T>?

    constructor(rxErrorHandler: RxErrorHandler, callback: ResponseCallback<T>) {
        errorHandlerFactory = rxErrorHandler.handlerFactory
        this.callback = callback
    }

    constructor(errorHandlerFactory: ErrorHandlerFactory?, compositeDisposable: CompositeDisposable?, callback: ResponseCallback<T>?) {
        this.errorHandlerFactory = errorHandlerFactory
        this.callback = callback
    }

    override fun onSubscribe(d: Disposable) {
        if (callback != null) {
            callback!!.onSubscribe(d)
        }
    }

    override fun onNext(data: T) {
        if (data is IBaseResponse) {
            val resultCode = (data as IBaseResponse).resultCode
            val errMsg = (data as IBaseResponse).errMsg
            val isSuccess = errorHandlerFactory!!.handlerResultCode(resultCode, errMsg)

            if (isSuccess) {
                callback?.onSuccess(resultCode, data)
            } else {
                if (!TextUtils.isEmpty(errMsg)) {
                    ToastUtils.show(errMsg)
                }
                callback?.onFailure(resultCode, errMsg)
            }
        } else {
            if (data == null) {
                callback?.onFailure(-1, "数据错误")
            } else {
                callback?.onFailure(-1, "解析异常")
            }
        }
    }

    override fun onComplete() {
        if (callback != null) {
            callback!!.onFinish()
        }
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        errorHandlerFactory!!.handlerError(e)
        if (callback != null) {
            callback!!.onFailure(-1, "未知异常")
            callback!!.onFinish()
        }
    }
}