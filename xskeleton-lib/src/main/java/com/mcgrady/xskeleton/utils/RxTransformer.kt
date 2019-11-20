package com.mcgrady.xskeleton.utils

import com.mcgrady.xskeleton.base.IView
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Incremental change is better than ambitious failure.
 *
 * @author : [MysticCoder](http://mysticcoder.coding.me)
 * @date : 2018/3/15
 * @desc :
 */
object RxTransformer {
    /**
     * 无参数  仅做切换线程
     *
     * @param <T> 泛型
     * @return 返回Observable
    </T> */
    fun <T> transform(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream: Observable<T> ->
            upstream
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 界面请求，不需要加载和隐藏loading时调用 使用RxLifeCycle
     * 传入view接口，Activity，Fragment等实现了view接口，Activity，Fragment继承于[com.trello.rxlifecycle2.components.support.RxAppCompatActivity]
     * 也就实现了bindToLifecycle方法
     * @param view View
     * @param <T> 泛型
     * @return
    </T> */
    fun <T> transform(view: IView): ObservableTransformer<T?, T> {
        return ObservableTransformer<T?, T> { observable: Observable<T?> ->
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(view.bindToLifecycle())
        }
    }

    /**
     * 界面请求，需要加载和隐藏loading时调用,使用RxLifeCycle
     * 传入view接口，Activity，Fragment等实现了view接口，Activity，Fragment继承于[com.trello.rxlifecycle2.components.support.RxAppCompatActivity]
     * 也就实现了bindToLifecycle方法
     * @param view View
     * @param <T> 泛型
     * @return
    </T> */
    fun <T> transformWithLoading(view: IView): ObservableTransformer<T?, T> { //隐藏进度条
        return ObservableTransformer<T?, T> { observable: Observable<T?> ->
            observable.subscribeOn(Schedulers.io())
                    .doOnSubscribe { disposable: Disposable? ->
                        view.showProgress() //显示进度条
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { view.hideProgress() }.compose(view.bindToLifecycle())
        }
    }
}