package com.mcgrady.common_social.login;

/**
 * @author: mcgrady
 * @date: 2019-05-27
 */
public class BaseToken {

    private String access_token;
    private String openId;

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
