
package com.mcgrady.xskeleton.lifecycle;

import android.app.Activity;

import androidx.annotation.NonNull;

import io.reactivex.subjects.Subject;

/**
 * 让 {@link Activity}/{@link Fragment} 实现此接口,即可正常使用 {@link RxLifecycle}
 * 无需再继承 {@link RxLifecycle} 提供的 Activity/Fragment ,扩展性极强
 *
 * Created by mcgrady on 2019/4/26.
 */
public interface Lifecycleable<E> {
    @NonNull
    Subject<E> provideLifecycleSubject();
}
