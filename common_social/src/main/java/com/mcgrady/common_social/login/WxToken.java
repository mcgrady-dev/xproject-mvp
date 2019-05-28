package com.mcgrady.common_social.login;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author: mcgrady
 * @date: 2019-05-27
 */
public class WxToken extends BaseToken {

    private String refreshToken;

    public static WxToken parse(JSONObject jsonObject) throws JSONException {
        WxToken wxToken = new WxToken();
        wxToken.setOpenId(jsonObject.getString("openid"));
        wxToken.setAccessToken(jsonObject.getString("access_token"));
        wxToken.setRefreshToken(jsonObject.getString("refresh_token"));
        return wxToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
