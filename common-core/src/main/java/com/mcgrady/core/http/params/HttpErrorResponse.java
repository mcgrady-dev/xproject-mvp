package com.mcgrady.core.http.params;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/6/21
 */

public class HttpErrorResponse extends Exception {

    private Throwable exception;
    private String type;

    public HttpErrorResponse(Throwable exception, String type) {
        this.exception = exception;
        this.type = type;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public HttpErrorResponse(String detailMessage, String type) {
        super(detailMessage);
        this.type = type;
    }

    @Override
    public String getMessage() {
        if (exception != null) {
            if (exception.getMessage() == null) {
                return exception.toString();
            } else {
                return exception.getMessage();
            }
        }
        if (super.getMessage() != null) {
            return super.getMessage();
        } else {
            return "未知错误";
        }
    }

    public String getType() {
        return type;
    }
}
