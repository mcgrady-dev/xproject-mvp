package com.mcgrady.common_social.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: mcgrady
 * @date: 2019-05-27
 */
public class QQLoginFetcher extends LoginFetcher<QQShareConfig> {

    private static String SCOPE = "get_simple_userinfo";
    private static String URL = "https://graph.qq.com/user/get_user_info";
    private Tencent tencent;
    private IUiListener uiListener;
    private LoginCallBack loginCallBack;
    private OkHttpClient client;

    public QQLoginFetcher(Activity activity, LoginCallBack callback, boolean fetchUserInfo) {
        super(activity, callback, fetchUserInfo);
        tencent = Tencent.createInstance(shareConfig.getQQId(), activity.getApplicationContext());
        loginCallBack = callback;
        client = new OkHttpClient();
        uiListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.i(TAG, "QQ auth success");
                try {
                    QQToken token = QQToken.parse((JSONObject) o);
                    if (fetchUserInfo) {
                        loginCallBack.beforeFetchUserInfo(token);
                        fetchUserInfo(token);
                    } else {
                        loginCallBack.loginSuccess(new LoginResult(LoginPlatform.QQ, token));
                    }
                } catch (JSONException e) {
                    Log.i(TAG, "Illegal token, please check your config");
                    loginCallBack.loginFailure(e);
                }
            }

            @Override
            public void onError(UiError uiError) {
                Log.i(TAG, "QQ login error");
                loginCallBack.loginFailure(new Exception("QQError: " + uiError.errorCode + " " + uiError.errorDetail));
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "QQ auth cancel");
                loginCallBack.loginCancel();
            }
        };
    }

    @Override
    public void doLogin(Activity activity, LoginCallBack callBack, boolean fetchUserInfo) {
        tencent.login(activity, SCOPE, uiListener);
    }

    @Override
    public void fetchUserInfo(BaseToken token) {
        Observable.fromCallable(() -> {
            Request request = new Request.Builder().url(buildUserInfoUrl(token, URL)).build();
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(response.body().string());
            return QQUser.parse(token.getOpenId(), jsonObject);
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(qqUser -> loginCallBack.loginSuccess(new LoginResult(LoginPlatform.QQ, token, qqUser)),
                throwable -> loginCallBack.loginFailure(new Exception(throwable)));
    }

    @Override
    public void handleResult(int requestCode, int resultCode, Intent data) {
        Tencent.handleResultData(data, uiListener);
    }

    @Override
    public boolean isInstall(Context context) {
        PackageManager pm = context.getPackageManager();
        if (pm == null) {
            return false;
        }

        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        for (PackageInfo info : packageInfos) {
            if (TextUtils.equals(info.packageName.toLowerCase(), "com.tencent.mobileqq")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void recycle() {
        tencent.releaseResource();
        uiListener = null;
        loginCallBack = null;
        tencent = null;
    }

    private String buildUserInfoUrl(BaseToken token, String base) {
        return base
                + "?access_token="
                + token.getAccessToken()
                + "&oauth_consumer_key="
                + shareConfig.getQQId()
                + "&openid="
                + token.getOpenId();
    }
}
