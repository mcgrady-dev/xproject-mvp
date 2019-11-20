package com.mcgrady.xskeleton.http.interf

import android.content.Context

/**
 * Created by mcgrady on 2019/4/26.
 */
interface ResponseErrorListener {
    fun handlerErrorResultCode(context: Context?, resultCode: Int, errMsg: String?): Boolean
    fun handlerResponseError(context: Context?, t: Throwable?) //ResponseErrorListener EMPTY = (context, t) -> {};
}