package com.mcgrady.xproject.base;

/**
 * Created by mcgrady on 2017/7/27.
 * Presenter基类
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void detachView();
}
