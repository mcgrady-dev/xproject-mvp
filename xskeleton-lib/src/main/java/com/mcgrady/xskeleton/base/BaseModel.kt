package com.mcgrady.xskeleton.base

import com.google.gson.Gson
import com.mcgrady.xskeleton.http.handler.RxErrorHandler
import com.mcgrady.xskeleton.integration.IRepositoryManager
import com.mcgrady.xskeleton.integration.RepositoryManager

/**
 * Created by mcgrady on 2019-08-10.
 */
open class BaseModel @JvmOverloads constructor(protected var mRepositoryManager: IRepositoryManager? = RepositoryManager.instance) : IModel {
    @kotlin.jvm.JvmField
    protected var mGson: Gson?
    val rxErrorHandler: RxErrorHandler?
        get() = mRepositoryManager?.rxErrorHandler

    override fun onDestroy() {
        mGson = null
    }

    init {
        mGson = Gson()
    }
}