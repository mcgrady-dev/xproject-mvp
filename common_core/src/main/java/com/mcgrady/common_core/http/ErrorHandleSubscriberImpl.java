package com.mcgrady.common_core.http;

import androidx.annotation.NonNull;

import com.mcgrady.xskeleton.http.handler.ErrorHandleSubscriber;
import com.mcgrady.xskeleton.http.handler.RxErrorHandler;

/**
 * Created by mcgrady on 2019-10-15.
 */
public class ErrorHandleSubscriberImpl<T> extends ErrorHandleSubscriber<T> {


    public ErrorHandleSubscriberImpl(@NonNull RxErrorHandler rxErrorHandler) {
        super(rxErrorHandler);
    }

    @Override
    public void onNext(T t) {

    }
}
