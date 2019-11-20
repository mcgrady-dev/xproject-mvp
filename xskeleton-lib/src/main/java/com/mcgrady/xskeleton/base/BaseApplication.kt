package com.mcgrady.xskeleton.base

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.hjq.toast.ToastUtils
import com.hjq.toast.style.ToastAliPayStyle

/**
 * Created by mcgrady on 2019-09-16.
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        ToastUtils.init(this, ToastAliPayStyle(this))
    }
}