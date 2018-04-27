package com.mcgrady.core.base;

import android.view.View;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/24
 * @des: View代理
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
