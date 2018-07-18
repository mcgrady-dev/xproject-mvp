package com.mcgrady.core.base;

/**
 * <p></p>
 * @author: mcgrady
 * @date: 2018/5/9
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
