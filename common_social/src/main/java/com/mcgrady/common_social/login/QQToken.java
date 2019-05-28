package com.mcgrady.common_social.login;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author: mcgrady
 * @date: 2019-05-27
 */
public class QQToken extends BaseToken {

    public static QQToken parse(JSONObject jsonObject) throws JSONException {
        QQToken token = new QQToken();
        token.setAccessToken(jsonObject.getString("access_token"));
        token.setOpenId(jsonObject.getString("openid"));
        return token;
    }
}
