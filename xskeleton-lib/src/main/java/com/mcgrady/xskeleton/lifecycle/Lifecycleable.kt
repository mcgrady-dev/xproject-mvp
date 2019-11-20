package com.mcgrady.xskeleton.lifecycle

import android.app.Activity
import com.trello.rxlifecycle3.RxLifecycle
import io.reactivex.subjects.Subject

/**
 * 让 [Activity]/[Fragment] 实现此接口,即可正常使用 [RxLifecycle]
 * 无需再继承 [RxLifecycle] 提供的 Activity/Fragment ,扩展性极强
 *
 * Created by mcgrady on 2019/4/26.
 */
interface Lifecycleable<E> {
    fun provideLifecycleSubject(): Subject<E>
}