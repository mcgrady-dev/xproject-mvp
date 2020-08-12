package com.mcgrady.common_social.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

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
public class WXLoginFetcher extends LoginFetcher<WXShareConfig> {

    public static final String SCOPE_USER_INFO = "snsapi_userinfo";
    private static final String SCOPE_BASE = "snsapi_base";
    private static final String BASE_URL = "https://api.weixin.qq.com/sns/";

    private IWXAPI iwxapi;
    private LoginCallBack loginCallBack;
    private OkHttpClient client;
    private boolean fetchUserInfo;


    public WXLoginFetcher(Activity activity, LoginCallBack callback, boolean fetchUserInfo) {
        super(activity, callback, fetchUserInfo);
        this.loginCallBack = callback;
        this.iwxapi = WXAPIFactory.createWXAPI(activity, shareConfig.getWxId());
        this.fetchUserInfo = fetchUserInfo;
        client = new OkHttpClient();
    }

    @Override
    public void doLogin(Activity activity, LoginCallBack callBack, boolean fetchUserInfo) {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = SCOPE_USER_INFO;
        req.state = String.valueOf(System.currentTimeMillis());
        iwxapi.sendReq(req);
    }

    @Override
    public void fetchUserInfo(BaseToken token) {

        Observable.fromCallable(() -> {
            Request request = new Request.Builder().url(buildUserInfoUrl(token)).build();
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(response.body().string());
            return WxUser.parse(jsonObject);
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(wxUser -> loginCallBack.loginSuccess(new LoginResult(LoginPlatform.WX, token, wxUser)),
                throwable -> loginCallBack.loginFailure(new Exception(throwable)));
    }

    @Override
    public void handleResult(int requestCode, int resultCode, Intent data) {
        iwxapi.handleIntent(data, new IWXAPIEventHandler() {
            @Override
            public void onReq(BaseReq baseReq) {

            }

            @Override
            public void onResp(BaseResp baseResp) {
                if (baseResp instanceof SendAuth.Resp && baseResp.getType() == 1) {
                    SendAuth.Resp resp = (SendAuth.Resp) baseResp;
                    switch (resp.errCode) {
                        case BaseResp.ErrCode.ERR_OK:
                            getToken(resp.code);
                            break;
                        case BaseResp.ErrCode.ERR_USER_CANCEL:
                            loginCallBack.loginCancel();
                            break;
                        case BaseResp.ErrCode.ERR_SENT_FAILED:
                            loginCallBack.loginFailure(new Exception("WeChat send failed"));
                            break;
                        case BaseResp.ErrCode.ERR_UNSUPPORT:
                            loginCallBack.loginFailure(new Exception("WeChat UnSupport"));
                            break;
                        case BaseResp.ErrCode.ERR_AUTH_DENIED:
                            loginCallBack.loginFailure(new Exception("Wechat auth denied"));
                            break;
                        default:
                            loginCallBack.loginFailure(new Exception("Wechat auth error"));
                            break;
                    }
                }
            }
        });
    }

    @Override
    public boolean isInstall(Context context) {
        return iwxapi.isWXAppInstalled();
    }

    @Override
    public void recycle() {
        if (iwxapi != null) {
            iwxapi.detach();
            iwxapi = null;
        }
    }

    private void getToken(String code) {

        Observable.fromCallable(() -> {
            Request request = new Request.Builder().url(buildTokenUrl(code)).build();
            Response response = client.newCall(request).execute();
            JSONObject jsonObject = new JSONObject(response.body().string());
            return WxToken.parse(jsonObject);
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(wxToken -> {
            if (fetchUserInfo) {
                loginCallBack.beforeFetchUserInfo(wxToken);
                fetchUserInfo(wxToken);
            } else {
                loginCallBack.loginSuccess(new LoginResult(LoginPlatform.WX, wxToken));
            }
        }, throwable -> loginCallBack.loginFailure(new Exception(throwable.getMessage())));
    }

    private String buildTokenUrl(String code) {
        return BASE_URL
                + "oauth2/access_token?appid="
                + shareConfig.getWxId()
                + "&secret="
                + shareConfig.getWxSecret()
                + "&code="
                + code
                + "&grant_type=authorization_code";
    }

    private String buildUserInfoUrl(BaseToken token) {
        return BASE_URL
                + "userinfo?access_token="
                + token.getAccessToken()
                + "&openid="
                + token.getOpenId();
    }
}
