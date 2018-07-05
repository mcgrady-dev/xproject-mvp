package com.mcgrady.core.http;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/6/21
 */

public abstract class BaseSubscriberListener<T> extends SubscriberListener<T> {


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        HttpExceptionHandler.RespondThrowable respondThrowable = HttpExceptionHandler.handleException(e);
    }
}
