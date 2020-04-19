package com.mcgrady.common_core.http;

import java.io.Serializable;

/**
 * Created by mcgrady on 2020/4/19.
 */
public class BaseResponse<T> implements Serializable {

    private T data;
    private String code;
    private String msg;

    public T getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return Api.REQUEST_SUCCESS.equals(code);
    }
}
