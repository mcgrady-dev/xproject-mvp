package com.mcgrady.xskeleton.http.handler

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by mcgrady on 2019/4/26.
 */
abstract class ErrorHandleSubscriber<T>(rxErrorHandler: RxErrorHandler) : Observer<T> {
    private val mHandlerFactory: ErrorHandlerFactory? = rxErrorHandler.handlerFactory
    override fun onSubscribe(d: Disposable) {}
    override fun onComplete() {}
    override fun onError(t: Throwable) {
        t.printStackTrace()
        //如果你某个地方不想使用全局错误处理,则重写 onError(Throwable) 并将 super.onError(e); 删掉
//如果你不仅想使用全局错误处理,还想加入自己的逻辑,则重写 onError(Throwable) 并在 super.onError(e); 后面加入自己的逻辑
        mHandlerFactory!!.handlerError(t)
    }

}