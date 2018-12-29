package com.mcgrady.core.base;

import android.view.View;

/**
 * <p>View代理</p>
 * @author: mcgrady
 * @date: 2018/5/9
 */
public interface IViewDelegate {

    void resume();

    void pause();

    void destory();

    void visible(boolean flag, View view);

    void gone(boolean flag, View view);

    void inVisible(View view);

    void toastShort(String msg);

    void toastLong(String msg);
}
