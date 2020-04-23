package com.mcgrady.xskeleton.base;


/**
 * Created by mcgrady on 2019-08-10.
 */
public interface IView {

    default void showProgress() {
    }

    default void hideProgress() {
    }

    default void finish() {

    }
}
