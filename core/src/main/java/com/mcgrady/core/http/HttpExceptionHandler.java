package com.mcgrady.core.http;

import android.support.annotation.NonNull;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;

import javax.security.auth.login.LoginException;

import retrofit2.HttpException;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/6/21
 */

public class HttpExceptionHandler {

    @NonNull
    public static RespondThrowable handleException(Throwable e) {

        RespondThrowable respondThrowable;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            respondThrowable = new RespondThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
//                case UNAUTHORIZED:
//                case FORBIDDEN:
//                case NOT_FOUND:
//                case REQUEST_TIMEOUT:
//                case GATEWAY_TIMEOUT:
//                case INTERNAL_SERVER_ERROR:
//                case BAD_GATEWAY:
//                case SERVICE_UNAVAILABLE:
                default:
                    respondThrowable.message = "网络错误";
                    break;
            }
            return respondThrowable;

        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            respondThrowable = new RespondThrowable(resultException, resultException.code);
            respondThrowable.message = resultException.message;
            return respondThrowable;

        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof RespondThrowable) {
            respondThrowable = new RespondThrowable(e, ERROR.PARSE_ERROR);
            respondThrowable.message = "解析错误";
            return respondThrowable;

        } else if (e instanceof ConnectException) {
            respondThrowable = new RespondThrowable(e, ERROR.NET_ERROR);
            respondThrowable.message = "连接失败";
            return respondThrowable;

        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
//            respondThrowable = new RespondThrowable(e, ERROR.SSL_ERROR);
//            respondThrowable.message = "证书验证失败";
//            return respondThrowable;

        } else if (e instanceof LoginException) {
            respondThrowable = new RespondThrowable(e, ERROR.LOGIN_ERROR);
            respondThrowable.message = "登录异常";
            return respondThrowable;

        } else if (e instanceof TimeOutException) {
            respondThrowable = new RespondThrowable(e, ERROR.TIME_OUT);
            respondThrowable.message = "超时";
            return respondThrowable;

        } else if (e instanceof NoDataException) {
            respondThrowable = new RespondThrowable(e, ERROR.LOGIN_ERROR);
            respondThrowable.message = "数据为空";
            return respondThrowable;

        } else if (e instanceof IllegalStateException) {
            respondThrowable = new RespondThrowable(e, ERROR.PARSE_ERROR);
            respondThrowable.message = "解析异常";
            return respondThrowable;

        } else if (e instanceof APIErrorException) {
            String code = ((APIErrorException) e).code;
            respondThrowable = new RespondThrowable(e, code);
            respondThrowable.message = e.getMessage();
            if (ERROR.LOGIN_ERROR == code) {
                // TO LOGIN ACTIVITY
                // ...

            } else if (ERROR.TIME_OUT == code) {

            } else {
                respondThrowable.message = e.getMessage();
            }

            return respondThrowable;
        }

        respondThrowable = new RespondThrowable(e, ERROR.UNKNOWN);
        respondThrowable.message = "未知异常";
        return respondThrowable;
    }


    /**
     * 服务异常
     */
    public class ServerException extends RuntimeException {
        public String code;
        public String message;
    }

    /**
     * 无数据异常
     */
    public class NoDataException extends RuntimeException {
        public String code;
        public String message;
    }

    /**
     * 超时异常
     */
    public class TimeOutException extends Exception {

    }

    /**
     * 接口异常
     */
    public static class APIErrorException extends IOException {
        public String code;

        public APIErrorException(String message, String code) {
            super(message);
            this.code = code;
        }
    }

    public static class RespondThrowable extends Exception {
        public String code;
        public String message;

        public RespondThrowable(Throwable throwable, String code) {
            super(throwable);
            this.code = code;

        }
    }

    public static class ERROR {

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
    }
}
