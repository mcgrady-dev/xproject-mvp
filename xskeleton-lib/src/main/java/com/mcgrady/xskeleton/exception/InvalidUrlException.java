package com.mcgrady.xskeleton.exception;

import android.text.TextUtils;

/**
 * Created by mcgrady on 2020/4/29.
 */
public class InvalidUrlException extends RuntimeException {
    public InvalidUrlException(String url) {
        super("You've configured an invalid url : " + (TextUtils.isEmpty(url) ? "EMPTY_OR_NULL_URL" : url));
    }
}
