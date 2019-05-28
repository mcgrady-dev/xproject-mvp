package com.mcgrady.common_social.login;

import android.content.Context;
import android.util.Log;

/**
 * @author: mcgrady
 * @date: 2019-05-27
 */
public class AuthLoginAPI {

    public static final String TAG = AuthLoginAPI.class.getSimpleName();

    public static final int TYPE = 799;

    private static LoginFetcher sLoginFetcher;

    private static  LoginCallBackProxy sLoginCallBack;

    private static int sPlatfrom;

    private static boolean sIsFetchUserInfo;

    public static void login(Context context, @LoginPlatform.Platform int platform, LoginCallBack callBack) {
        login(context, platform, callBack, true);
    }

    public static void login(Context context, @LoginPlatform.Platform int platform, LoginCallBack callBack,
                             boolean fetchUserInfo) {
        sPlatfrom = platform;
        sLoginCallBack = new LoginCallBackProxy(callBack);
        sIsFetchUserInfo = fetchUserInfo;
//        context.startActivity(_Share);

    }

    public static void recycle() {

    }

    private static class LoginCallBackProxy implements LoginCallBack {

        private LoginCallBack loginCallBack;

        public LoginCallBackProxy(LoginCallBack loginCallBack) {
            this.loginCallBack = loginCallBack;
        }

        @Override
        public void loginSuccess(LoginResult result) {
            Log.i(TAG, "login success");
            loginCallBack.loginSuccess(result);
            recycle();
        }

        @Override
        public void loginFailure(Exception e) {
            Log.i(TAG, "login failed");
            loginCallBack.loginFailure(e);
            recycle();
        }

        @Override
        public void loginCancel() {
            Log.i(TAG, "login cancel");
            loginCallBack.loginCancel();
            recycle();
        }

        @Override
        public void beforeFetchUserInfo(BaseToken token) {
            Log.i(TAG, "before fetch user info");
            loginCallBack.beforeFetchUserInfo(token);
        }
    }
}
