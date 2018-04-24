package com.mcgrady.core.base;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/24
 * @des: MVP Presenter基类
 */
public interface IBasePresenter<V extends IBaseView> {

    void attachView(V view);

    void detachView();
}
