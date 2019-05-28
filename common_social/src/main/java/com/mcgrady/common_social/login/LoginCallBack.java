package com.mcgrady.common_social.login;

/**
 * @author: mcgrady
 * @date: 2019-05-27
 */
public interface LoginCallBack {

    void loginSuccess(LoginResult result);

    default void beforeFetchUserInfo(BaseToken token) {
    }

    void loginFailure(Exception e);

    void loginCancel();
}
