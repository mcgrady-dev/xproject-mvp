package com.mcgrady.xskeleton.utils

import com.mcgrady.xskeleton.base.IView
import com.mcgrady.xskeleton.lifecycle.ActivityLifecycleable
import com.mcgrady.xskeleton.lifecycle.FragmentLifecycleable
import com.mcgrady.xskeleton.lifecycle.Lifecycleable
import com.trello.rxlifecycle3.LifecycleTransformer
import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.android.FragmentEvent
import com.trello.rxlifecycle3.android.RxLifecycleAndroid

/**
 * RxLifecycle 工具栏
 *
 * Created by mcgrady on 2019/4/26.
 */
class RxLifecycleUtils private constructor() {
    companion object {
        /**
         * 绑定 Activity 的指定生命周期
         *
         * @param view
         * @param event
         * @param <T>
         * @return
        </T> */
        fun <T> bindUntilEvent(view: IView,
                               event: ActivityEvent): LifecycleTransformer<T> {
            Preconditions.Companion.checkNotNull(view, "view == null")
            return if (view is ActivityLifecycleable) {
                bindUntilEvent(view as ActivityLifecycleable, event)
            } else {
                throw IllegalArgumentException("view isn't ActivityLifecycleable")
            }
        }

        /**
         * 绑定 Fragment 的指定生命周期
         *
         * @param view
         * @param event
         * @param <T>
         * @return
        </T> */
        fun <T> bindUntilEvent(view: IView,
                               event: FragmentEvent): LifecycleTransformer<T> {
            Preconditions.Companion.checkNotNull(view, "view == null")
            return if (view is FragmentLifecycleable) {
                bindUntilEvent(view as FragmentLifecycleable, event)
            } else {
                throw IllegalArgumentException("view isn't FragmentLifecycleable")
            }
        }

        fun <T, R> bindUntilEvent(lifecycleable: Lifecycleable<R>,
                                  event: R): LifecycleTransformer<T> {
            Preconditions.Companion.checkNotNull(lifecycleable, "lifecycleable == null")
            return RxLifecycle.bindUntilEvent(lifecycleable.provideLifecycleSubject(), event)
        }

        /**
         * 绑定 Activity/Fragment 的生命周期
         *
         * @param view
         * @param <T>
         * @return
        </T> */
        fun <T> bindToLifecycle(view: IView): LifecycleTransformer<T> {
            Preconditions.Companion.checkNotNull(view, "view == null")
            return if (view is Lifecycleable<*>) {
                bindToLifecycle(view as Lifecycleable<*>)
            } else {
                throw IllegalArgumentException("view isn't Lifecycleable")
            }
        }

        fun <T> bindToLifecycle(lifecycleable: Lifecycleable<*>): LifecycleTransformer<T> {
            Preconditions.Companion.checkNotNull(lifecycleable, "lifecycleable == null")
            return if (lifecycleable is ActivityLifecycleable) {
                RxLifecycleAndroid.bindActivity(lifecycleable.provideLifecycleSubject())
            } else if (lifecycleable is FragmentLifecycleable) {
                RxLifecycleAndroid.bindFragment(lifecycleable.provideLifecycleSubject())
            } else {
                throw IllegalArgumentException("Lifecycleable not match")
            }
        }
    }

    init {
        throw IllegalStateException("you can't instantiate me!")
    }
}