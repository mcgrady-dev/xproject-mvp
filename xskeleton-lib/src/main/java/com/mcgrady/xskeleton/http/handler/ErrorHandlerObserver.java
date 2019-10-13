package com.mcgrady.xskeleton.http.handler;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.hjq.toast.ToastUtils;
import com.mcgrady.xskeleton.http.interf.IBaseResponse;
import com.mcgrady.xskeleton.http.interf.ResponseCallback;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by mcgrady on 2019-08-22.
 */
public class ErrorHandlerObserver<T> implements Observer<T> {

    private ErrorHandlerFactory errorHandlerFactory;
    private ResponseCallback callback;

    public ErrorHandlerObserver(@NonNull RxErrorHandler rxErrorHandler, @NonNull ResponseCallback<T> callback) {
        this.errorHandlerFactory = rxErrorHandler.getHandlerFactory();
        this.callback = callback;
    }


    public ErrorHandlerObserver(ErrorHandlerFactory errorHandlerFactory, CompositeDisposable compositeDisposable, ResponseCallback callback) {
        this.errorHandlerFactory = errorHandlerFactory;
        this.callback = callback;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (callback != null) {
            callback.onSubscribe(d);
        }
    }

    @Override
    public void onNext(T data) {
        if (data instanceof IBaseResponse) {
            int resultCode = ((IBaseResponse) data).getResultCode();
            String errMsg = ((IBaseResponse) data).getErrMsg();

            boolean isSuccess = errorHandlerFactory.handlerResultCode(resultCode, errMsg);

            if (callback != null) {
                if (isSuccess) {
                    callback.onSuccess(resultCode, data);
                } else {
                    if (!TextUtils.isEmpty(errMsg)) {
                        ToastUtils.show(errMsg);
                    }
                    callback.onFailure(resultCode, errMsg);
                }
            }
        } else {
            if (callback != null) {
                if (data == null) {
                    callback.onFailure(-1, "数据错误");
                } else {
                    callback.onFailure(-1, "解析异常");
                }
            }
        }
    }

    @Override
    public void onComplete() {
        if (callback != null) {
            callback.onFinish();
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        errorHandlerFactory.handlerError(e);

        if (callback != null) {
            callback.onFailure(-1, "未知异常");
            callback.onFinish();
        }
    }
}
