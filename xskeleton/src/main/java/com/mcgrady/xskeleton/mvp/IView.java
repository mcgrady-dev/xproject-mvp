package com.mcgrady.xskeleton.mvp;

/**
 * Created by mcgrady on 2019/4/25.
 */
public interface IView {

    default void showProgress() {
    }

    default void hideProgress() {
    }

    default void finish() {

    }
}
