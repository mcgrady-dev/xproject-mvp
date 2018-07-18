package com.mcgrady.core.http;

import retrofit2.HttpException;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/6/21
 */

public abstract class SubscriberCallback<T> {

    /**
     * 成功
     *
     * @param response
     */
    public abstract void onSuccess(T response);

    /**
     * 最终错误处理
     *
     * @param error
     */
    public abstract void onFail(HttpErrorException error);

    /**
     * 抛出异常,还是在onFail中处理
     *
     * @param e
     */
    public abstract void onError(Throwable e);

    /**
     * end
     */
    public void onCompleted() {
    }

    /**
     * begin
     */
    public void onBegin() {
    }

    /**
     * 是否显示dialog
     *
     * @return
     */
    public boolean isShowLoading(){
        return true;
    }

    /**
     * 是否需要重新登录
     *
     * @return
     * @param httpException
     */
    public boolean isCheckReLogin(HttpException httpException) {
        return false;
    }

    /**
     * 重新登录处理
     *
     * @param errorCode
     * @param errorMsg
     */
    public abstract void checkReLogin(String errorCode,String errorMsg);
}
