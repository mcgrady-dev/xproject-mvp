package com.mcgrady.core.base;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/24
 * @des: MVP View基类
 */
public interface IBaseView {

    <T> LifecycleTransformer<T> bindLifecycle();

    Context getAContext();
}
