package com.mcgrady.xskeleton.mvp;

import com.trello.rxlifecycle3.LifecycleTransformer;

/**
 * Created by mcgrady on 2019/4/25.
 */
public interface IView {

    <T> LifecycleTransformer<T> bindToLifecycle();

    default void showProgress() {
    }

    default void hideProgress() {
    }

    default void finish() {

    }
}
