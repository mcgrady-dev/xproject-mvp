package com.mcgrady.xproject.base;

/**
 * Created by mcgrady on 2017/7/27.
 * View基类
 */

public interface BaseView {

    void showErrorMsg(String msg);

    void stateError();

    void stateEmpty();

    void stateLoading();

    void stateMain();
}
