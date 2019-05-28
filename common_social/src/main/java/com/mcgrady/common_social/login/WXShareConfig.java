package com.mcgrady.common_social.login;

/**
 * @author: mcgrady
 * @date: 2019-05-27
 */
public class WXShareConfig implements ShareConfig {

    private String wxId;

    private String wxSecret;

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getWxSecret() {
        return wxSecret;
    }

    public void setWxSecret(String wxSecret) {
        this.wxSecret = wxSecret;
    }
}
