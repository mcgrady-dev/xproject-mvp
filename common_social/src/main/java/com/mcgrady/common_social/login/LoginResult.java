package com.mcgrady.common_social.login;

/**
 * @author: mcgrady
 * @date: 2019-05-27
 */
public class LoginResult {
    private BaseToken token;

    private BaseUser userInfo;

    private int platform;

    public LoginResult(int platform, BaseToken token, BaseUser userInfo) {
        this.platform = platform;
        this.token = token;
        this.userInfo = userInfo;
    }

    public LoginResult(int platform, BaseToken token) {
        this.platform = platform;
        this.token = token;
    }

    public BaseToken getToken() {
        return token;
    }

    public void setToken(BaseToken token) {
        this.token = token;
    }

    public BaseUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(BaseUser userInfo) {
        this.userInfo = userInfo;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }
}
