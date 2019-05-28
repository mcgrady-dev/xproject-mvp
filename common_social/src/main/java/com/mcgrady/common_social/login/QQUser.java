package com.mcgrady.common_social.login;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author: mcgrady
 * @date: 2019-05-27
 */
class QQUser extends BaseUser {

    private String qZoneHeadImage;
    private String qZoneHeadImageLarge;

    public static QQUser parse(String openId, JSONObject jsonObject) throws JSONException {
        QQUser user = new QQUser();
        user.setNickName(jsonObject.getString("nickname"));
        user.setOpenId(openId);
        user.setSex(TextUtils.equals("男", jsonObject.getString("gender")) ? 1 : 2);
        user.setHeadImageUrl(jsonObject.getString("figureurl_qq_1"));
        user.setHeadImageUrlLarge(jsonObject.getString("figureurl_qq_2"));
        user.setqZoneHeadImage(jsonObject.getString("figureurl_1"));
        user.setqZoneHeadImageLarge(jsonObject.getString("figureurl_2"));

        return user;
    }

    public String getqZoneHeadImage() {
        return qZoneHeadImage;
    }

    public void setqZoneHeadImage(String qZoneHeadImage) {
        this.qZoneHeadImage = qZoneHeadImage;
    }

    public String getqZoneHeadImageLarge() {
        return qZoneHeadImageLarge;
    }

    public void setqZoneHeadImageLarge(String qZoneHeadImageLarge) {
        this.qZoneHeadImageLarge = qZoneHeadImageLarge;
    }
}
