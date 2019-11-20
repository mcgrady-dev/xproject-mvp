package com.mcgrady.xskeleton.base

import com.trello.rxlifecycle3.LifecycleTransformer

/**
 * Created by mcgrady on 2019-08-10.
 */
interface IView {
    fun <T> bindToLifecycle(): LifecycleTransformer<T>?
    fun showProgress() {}
    fun hideProgress() {}
    fun finish() {}
}