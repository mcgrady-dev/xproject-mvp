package com.mcgrady.xskeleton.http.handler;

import android.content.Context;

import com.mcgrady.xskeleton.http.interf.ResponseCallback;
import com.mcgrady.xskeleton.http.interf.ResponseErrorListener;
import com.mcgrady.xskeleton.http.interf.ResponseResultCodeListener;

/**
 * Created by mcgrady on 2019/4/26.
 */
public class ErrorHandlerFactory {
    public final String TAG = this.getClass().getSimpleName();
    private Context context;
    private ResponseErrorListener mResponseErrorListener;

    public ErrorHandlerFactory(Context context, ResponseErrorListener responseErrorListener) {
        this.context = context;
        this.mResponseErrorListener = responseErrorListener;
    }

    public void handlerError(Throwable throwable) {
        mResponseErrorListener.handlerResponseError(context, throwable);
    }
    /**
     *
     * @param resultCode
     * @param errMsg
     * @return isSuccess
     */
    public boolean handlerResultCode(int resultCode, String errMsg) {
        return mResponseErrorListener.handlerErrorResultCode(context, resultCode, errMsg);
    }
}
