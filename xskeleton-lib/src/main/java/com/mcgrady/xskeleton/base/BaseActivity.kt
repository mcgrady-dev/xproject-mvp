package com.mcgrady.xskeleton.base

import android.os.Bundle
import android.view.InflateException
import butterknife.ButterKnife
import butterknife.Unbinder
import com.gyf.immersionbar.ImmersionBar
import com.mcgrady.xskeleton.lifecycle.ActivityLifecycleable
import com.mcgrady.xskeleton.widget.LoadingDialog
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

/**
 * Created by mcgrady on 2019-08-10.
 */
abstract class BaseActivity<P : IPresenter?> : RxAppCompatActivity(), IActivity, ActivityLifecycleable {
    protected val TAG = this.javaClass.simpleName
    private val mLifecycleSubject = BehaviorSubject.create<ActivityEvent>()
    private var mUnbinder: Unbinder? = null
    private var loadingDialog: LoadingDialog? = null
    @kotlin.jvm.JvmField
    protected var mPresenter: P? = null
    protected abstract fun createPresenter(): P
    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = createPresenter()
        super.onCreate(savedInstanceState)
        try {
            val layoutResId = layoutResId
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResId != 0) {
                setContentView(layoutResId)
                mUnbinder = ButterKnife.bind(this)
                initImmersionBar()
                initView(savedInstanceState)
            }
        } catch (e: Exception) {
            if (e is InflateException) throw e
            e.printStackTrace()
        }
        initData(savedInstanceState)
    }

    override fun provideLifecycleSubject(): Subject<ActivityEvent?> {
        return mLifecycleSubject
    }

    protected open fun initImmersionBar() {
        ImmersionBar.with(this)
                .keyboardEnable(true)
                .transparentBar()
                .statusBarDarkFont(true)
                .fullScreen(false)
                .init()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mUnbinder != null && mUnbinder !== Unbinder.EMPTY) {
            mUnbinder!!.unbind()
        }
        mUnbinder = null

        mPresenter?.onDestroy() //释放资源

        mPresenter = null
        //如果要使用 Eventbus 请将此方法返回 true
        if (useEventBus()) { //解除注册 Eventbus
//EventBus.getDefault().unregister(this);
        }
    }

    override fun showProgress() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(this)
        }
        if (!loadingDialog!!.isShowing) {
            loadingDialog!!.show()
        }
    }

    override fun hideProgress() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }
    }

    override fun useEventBus(): Boolean {
        return false
    }
}