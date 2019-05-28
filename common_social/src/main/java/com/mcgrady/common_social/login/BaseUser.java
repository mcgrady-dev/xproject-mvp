package com.mcgrady.common_social.login;

/**
 * @author: mcgrady
 * @date: 2019-05-27
 */
public class BaseUser {

    private String openId;
    private String nickName;
    private int sex;
    private String headImageUrl;
    private String headImageUrlLarge;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getHeadImageUrlLarge() {
        return headImageUrlLarge;
    }

    public void setHeadImageUrlLarge(String headImageUrlLarge) {
        this.headImageUrlLarge = headImageUrlLarge;
    }
}
