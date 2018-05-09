package com.mcgrady.core.base;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * <p>MVP View基类</p>
 * @author: mcgrady
 * @date: 2018/5/9
 */
public interface IBaseView {

    <T> LifecycleTransformer<T> bindLifecycle();

    Context getAContext();
}
