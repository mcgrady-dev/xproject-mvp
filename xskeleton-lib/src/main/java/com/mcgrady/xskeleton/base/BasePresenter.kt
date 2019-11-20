package com.mcgrady.xskeleton.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by mcgrady on 2019-08-10.
 */
open class BasePresenter<M : IModel?, V : IView?>(mModel: IModel?, mView: IView?) : IPresenter {
    protected val TAG = this.javaClass.simpleName
    protected var mCompositeDisposable: CompositeDisposable? = null
    @kotlin.jvm.JvmField
    protected var mModel: M?
    @kotlin.jvm.JvmField
    protected var mView: V?

    constructor(view: V?) : this(null, view) {}

    override fun onStart() {}
    /**
     * 添加 [Disposable] 统一管理
     * @param disposable
     */
    fun addDispose(disposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable!!)
    }

    /**
     * 解除订阅
     * 停止集合中正在进行的 RxJava 任务，保证 Activity 结束时取消所有正在执行的订阅
     */
    fun unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.clear()
        }
    }

    override fun onDestroy() {
        unDispose()
        mModel?.onDestroy()
    }

    init {
        this.mModel = mModel as M?
        this.mView = mView as V?
    }
}