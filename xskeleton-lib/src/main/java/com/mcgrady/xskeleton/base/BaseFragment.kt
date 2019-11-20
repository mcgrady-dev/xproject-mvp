package com.mcgrady.xskeleton.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.mcgrady.xskeleton.lifecycle.FragmentLifecycleable
import com.mcgrady.xskeleton.widget.LoadingDialog
import com.trello.rxlifecycle3.android.FragmentEvent
import com.trello.rxlifecycle3.components.support.RxFragment
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

/**
 * Created by mcgrady on 2019/4/26.
 */
abstract class BaseFragment<P : IPresenter?> : RxFragment(), IFragment, FragmentLifecycleable {
    protected val TAG = this.javaClass.simpleName
    private val mLifecycleSubject = BehaviorSubject.create<FragmentEvent>()
    protected var mContext: Context? = null
    private var mUnbinder: Unbinder? = null
    private var rootView: View? = null
    private var loadingDialog: LoadingDialog? = null
    protected var mPresenter //如果当前页面逻辑简单, Presenter 可以为 null
            : P? = null

    protected abstract fun createPresenter(): P
    override fun provideLifecycleSubject(): Subject<FragmentEvent?> {
        return mLifecycleSubject
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(layoutResId, container, false)
            mUnbinder = ButterKnife.bind(this, rootView!!)
            initView(savedInstanceState)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fitsLayoutOverlap()
    }

    protected fun fitsLayoutOverlap() {}
    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy() //释放资源
        mPresenter = null
    }

    override fun onDetach() {
        super.onDetach()
        if (mUnbinder != null && mUnbinder !== Unbinder.EMPTY) {
            mUnbinder!!.unbind()
        }
        mUnbinder = null
        mContext = null
    }

    override fun showProgress() {
        if (mContext is IActivity) {
            (mContext as IActivity).showProgress()
        } else {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog(mContext!!)
            }
            if (!loadingDialog!!.isShowing) {
                loadingDialog!!.show()
            }
        }
    }

    override fun hideProgress() {
        if (mContext is IActivity) {
            (mContext as IActivity).hideProgress()
        } else {
            if (loadingDialog != null && loadingDialog!!.isShowing) {
                loadingDialog!!.dismiss()
            }
        }
    }
}