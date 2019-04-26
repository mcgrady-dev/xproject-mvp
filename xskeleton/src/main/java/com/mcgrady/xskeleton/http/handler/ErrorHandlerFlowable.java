package com.mcgrady.xskeleton.http.handler;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by mcgrady on 2019/4/26.
 */
public abstract class ErrorHandlerFlowable<T> implements Subscriber<T> {
    private ErrorHandlerFactory mHandlerFactory;

    public ErrorHandlerFlowable(RxErrorHandler rxErrorHandler) {
        this.mHandlerFactory = rxErrorHandler.getHandlerFactory();
    }

    @Override
    public void onSubscribe(Subscription s) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();

        /**
         * 如果你某个地方不想使用全局错误处理,则重写 onError(Throwable) 并将 super.onError(e); 删掉
         * 如果你不仅想使用全局错误处理,还想加入自己的逻辑,则重写 onError(Throwable) 并在 super.onError(e); 后面加入自己的逻辑
         */
        mHandlerFactory.handlerError(t);
    }
}
