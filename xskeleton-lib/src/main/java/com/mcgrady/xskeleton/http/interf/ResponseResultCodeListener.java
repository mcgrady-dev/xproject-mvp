package com.mcgrady.xskeleton.http.interf;

import android.content.Context;

/**
 * Created by mcgrady on 2019-08-22.
 */
public interface ResponseResultCodeListener {

    boolean handlerResultCode(Context context, int resultCode, String errMsg);
}
