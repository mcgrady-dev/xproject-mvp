package com.mcgrady.xskeleton.http.handler;

import io.reactivex.SingleObserver;

/**
 * Created by mcgrady on 2020/8/14.
 */
public abstract class ErrorHandlerSingle<T> implements SingleObserver<T> {
    private ErrorHandlerFactory mHandlerFactory;


    public ErrorHandlerSingle(ErrorHandlerFactory errorHandlerFactory) {
        this.mHandlerFactory = errorHandlerFactory;
    }

    @Override
    public void onSuccess(T t) {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        mHandlerFactory.handlerError(e);
    }
}
