package com.mcgrady.xskeleton.http.interf;

import android.content.Context;

/**
 * Created by mcgrady on 2019/4/26.
 */
public interface ResponseErrorListener {
    boolean handlerErrorResultCode(Context context, int resultCode, String errMsg);

    void handlerResponseError(Context context, Throwable t);

    //ResponseErrorListener EMPTY = (context, t) -> {};
}
