package com.mcgrady.xskeleton.http.handler;


import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by mcgrady on 2019/4/26.
 */
public abstract class ErrorHandleSubscriber<T> implements Observer<T> {
    protected ErrorHandlerFactory handlerFactory;

    private ErrorHandleSubscriber() {}

    public ErrorHandleSubscriber(RxErrorHandler rxErrorHandler){
        this.handlerFactory = rxErrorHandler.getHandlerFactory();
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(@NonNull Throwable t) {
        t.printStackTrace();
        //如果你某个地方不想使用全局错误处理,则重写 onError(Throwable) 并将 super.onError(e); 删掉
        //如果你不仅想使用全局错误处理,还想加入自己的逻辑,则重写 onError(Throwable) 并在 super.onError(e); 后面加入自己的逻辑
        handlerFactory.handlerError(t);
    }
}
