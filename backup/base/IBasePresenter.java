package com.mcgrady.core.base;

/**
 * <p>MVP Presenter基类</p>
 * @author: mcgrady
 * @date: 2018/5/9
 */
public interface IBasePresenter<V extends IBaseView> {

    void attachView(V view);

    void detachView();
}
