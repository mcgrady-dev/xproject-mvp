package com.mcgrady.xskeleton.base

import android.os.Bundle
import androidx.annotation.LayoutRes

/**
 * Created by mcgrady on 2019-08-10.
 */
interface IActivity {
    @get:LayoutRes
    val layoutResId: Int

    fun initView(savedInstanceState: Bundle?)
    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    fun initData(savedInstanceState: Bundle?)

    /**
     * 是否使用 EventBus
     *
     * @return
     */
    fun useEventBus(): Boolean

    fun showProgress()
    fun hideProgress()
}