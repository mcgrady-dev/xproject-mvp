package com.mcgrady.core.http;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/7/18
 */

public class HttpErrorException extends Exception {

    /**
     * 没有权限
     */
    public static final String AUTH_ERROR = "-2";

    /**
     * 登录异常
     */
    public static final String LOGIN_ERROR = "";

    /**
     * 无数据返回异常
     */
    public static final String NO_DATA_ERROR = "-3";

    /**
     * 未知错误
     */
    public static final String UNKNOWN = "-5";

    /**
     * 解析错误
     */
    public static final String PARSE_ERROR = "0";

    /**
     * 无连接异常
     */
    public static final String NO_CONNECT_ERROR = "-1";

    /**
     * 请求错误
     */
    public static final String NET_ERROR = "-7";

    /**
     * 请求超时
     */
    public static final String TIME_OUT = "-6";

    /**
     * 证书出错
     */
    public static final String SSL_ERROR = "1005";

    /**
     * 协议出错
     */
    public static final String HTTP_ERROR = "1003";

    private Throwable exception;
    private String code;

    public HttpErrorException(String message, String code) {
        super(message);
        this.code = code;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public String getCode() {
        return code;
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
}
