package com.mcgrady.xskeleton.utils

import com.blankj.utilcode.util.LogUtils

/**
 *
 * 检查View重复点击工具
 *
 * @author: mcgrady
 * @date: 2018/9/3
 */
object MultiClickUtils {
    private const val CLICK_INTERVAL = 500L
    private var lastClickTime = 0L
    /**
     * 重置点击过滤器, 在每次用代码模拟View点击事件的时候, 要先reset一下, 以防被过滤掉
     */
    fun resetMultiClick() {
        lastClickTime = 0L
    }

    val isMultiClick: Boolean
        get() = isMultiClick(CLICK_INTERVAL)

    fun isMultiClick(limitTime: Long): Boolean {
        val time = System.currentTimeMillis()
        LogUtils.d("current click time $time")
        LogUtils.d("last click time $lastClickTime")
        if (time - lastClickTime > limitTime) {
            lastClickTime = time
            return false
        }
        return true
    }
}