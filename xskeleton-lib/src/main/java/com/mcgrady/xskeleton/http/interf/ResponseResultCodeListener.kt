package com.mcgrady.xskeleton.http.interf

import android.content.Context

/**
 * Created by mcgrady on 2019-08-22.
 */
interface ResponseResultCodeListener {
    fun handlerResultCode(context: Context?, resultCode: Int, errMsg: String?): Boolean
}