package com.mcgrady.common_core.http;

/**
 * Created by mcgrady on 2020/4/19.
 */
public class ResponseException extends Exception {

    private String errorCode;
    private String errorMsg;

    public ResponseException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
