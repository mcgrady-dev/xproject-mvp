package com.mcgrady.core.account;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;

/**
 * <p>用户SP工具类</p>
 * @author: mcgrady
 * @date: 2018/5/10
 */
public class AuthPreferences {

    private static final String KEY_USER = "user";
    private static final String KEY_ACCESSTOKEN = "accessToken";
    private static final String KEY_USER_DATA = "user_data";
    private static final String KEY_QR_DATA = "qr_data";
    private Context mContext;

    public AuthPreferences(Context context) {
        mContext = context;
    }

    public void setUser(String user) {
        SPUtils.getInstance().put(KEY_USER, user);
    }

    public void setAccessToken(String token) {
        SPUtils.getInstance().put(KEY_ACCESSTOKEN, token);
    }

    public void setUserData(String userData) {
        SPUtils.getInstance().put(KEY_USER_DATA, userData);
    }


    public String getUser() {
        return SPUtils.getInstance().getString(KEY_USER);
    }

    public String getAccessToken() {
        return SPUtils.getInstance().getString(KEY_ACCESSTOKEN);
    }

    public String getQRData(){
        return SPUtils.getInstance().getString(KEY_QR_DATA);
    }

    public void setQRData(String qrData){
        SPUtils.getInstance().put(KEY_QR_DATA, qrData);
    }

    public String getUserData() {
        return (String) SPUtils.getInstance().getString(KEY_USER_DATA);
    }

    public void clear() {
        SPUtils.getInstance().clear();
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(getAccessToken());
    }

}
