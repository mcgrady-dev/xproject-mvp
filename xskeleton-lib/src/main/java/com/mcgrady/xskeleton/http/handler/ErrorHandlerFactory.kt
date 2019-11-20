package com.mcgrady.xskeleton.http.handler

import android.content.Context
import com.mcgrady.xskeleton.http.interf.ResponseErrorListener

/**
 * Created by mcgrady on 2019/4/26.
 */
class ErrorHandlerFactory(private val context: Context, private val mResponseErrorListener: ResponseErrorListener) {
    val TAG = this.javaClass.simpleName
    fun handlerError(throwable: Throwable?) {
        mResponseErrorListener.handlerResponseError(context, throwable)
    }

    /**
     *
     * @param resultCode
     * @param errMsg
     * @return isSuccess
     */
    fun handlerResultCode(resultCode: Int, errMsg: String?): Boolean {
        return mResponseErrorListener.handlerErrorResultCode(context, resultCode, errMsg)
    }

}