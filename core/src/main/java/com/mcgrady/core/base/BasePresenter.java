package com.mcgrady.core.base;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/24
 * @des:
 */
public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    private V mView;

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    protected V getV() {
        if (mView == null) {
            throw new IllegalStateException("view can not be null");
        }

        return mView;
    }
}
