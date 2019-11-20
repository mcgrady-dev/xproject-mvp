package com.mcgrady.xskeleton.base

import android.os.Bundle

/**
 * 懒加载Fragment
 * 子类覆写[BaseLazyLoadFragment.lazyLoadData]可快速实现Fragment懒加载
 *
 * Created by mcgrady on 2019/4/26.
 */
abstract class BaseLazyLoadFragment<P : IPresenter?> : BaseFragment<P>() {

    private var isViewCreated = false   // 界面是否已创建完成 = false
    private var isVisibleToUser = false // 是否对用户可见 = false
    private var isDataLoaded = false    // 数据是否已请求 = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        tryLoadData()
    }

    /**
     * 保证在initData后触发
     */
    override fun onResume() {
        super.onResume()
        isViewCreated = true
        tryLoadData()
    }

    /**
     * ViewPager场景下，判断父fragment是否可见
     */
    private val isParentVisible: Boolean
        private get() {
            val fragment = parentFragment
            return fragment == null || fragment is BaseLazyLoadFragment<*> && fragment.isVisibleToUser
        }

    /**
     * ViewPager场景下，当前fragment可见时，如果其子fragment也可见，则让子fragment请求数据
     */
    private fun dispatchParentVisibleState() {
        val fragmentManager = childFragmentManager
        val fragments = fragmentManager.fragments
        if (fragments.isEmpty()) {
            return
        }
        for (child in fragments) {
            if (child is BaseLazyLoadFragment<*> && child.isVisibleToUser) {
                child.tryLoadData()
            }
        }
    }

    fun tryLoadData() {
        if (isViewCreated && isVisibleToUser && isParentVisible && !isDataLoaded) {
            lazyLoadData()
            isDataLoaded = true
            //通知子Fragment请求数据
            dispatchParentVisibleState()
        }
    }

    /**
     * 第一次可见时触发调用,此处实现具体的数据请求逻辑
     */
    protected abstract fun lazyLoadData()

    override fun initData(savedInstanceState: Bundle?) {}
}