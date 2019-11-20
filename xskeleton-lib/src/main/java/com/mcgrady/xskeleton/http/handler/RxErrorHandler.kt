package com.mcgrady.xskeleton.http.handler

import android.content.Context
import com.mcgrady.xskeleton.http.interf.ResponseErrorListener

/**
 * Created by mcgrady on 2019/4/26.
 */
class RxErrorHandler private constructor(builder: Builder) {
    val TAG = this.javaClass.simpleName
    val handlerFactory: ErrorHandlerFactory?

    class Builder {
        private var context: Context? = null
        private var mResponseErrorListener: ResponseErrorListener? = null
        var errorHandlerFactory: ErrorHandlerFactory? = null
        fun with(context: Context?): Builder {
            this.context = context
            return this
        }

        fun responseErrorListener(responseErrorListener: ResponseErrorListener?): Builder {
            mResponseErrorListener = responseErrorListener
            return this
        }

        fun build(): RxErrorHandler {
            checkNotNull(context) { "Context is required" }
            checkNotNull(mResponseErrorListener) { "ResponseErrorListener is required" }
            errorHandlerFactory = ErrorHandlerFactory(context!!, mResponseErrorListener!!)
            return RxErrorHandler(this)
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    init {
        handlerFactory = builder.errorHandlerFactory
    }
}