package com.mcgrady.common_social.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * @author: mcgrady
 * @date: 2019-05-27
 */
public abstract class LoginFetcher<T extends ShareConfig> {

    protected final String TAG = this.getClass().getSimpleName();

    protected T shareConfig;

    public LoginFetcher(Activity activity, LoginCallBack callback, boolean fetchUserInfo) {

    }

    public abstract void doLogin(Activity activity, LoginCallBack callBack, boolean fetchUserInfo);

    public abstract void fetchUserInfo(BaseToken token);

    public abstract void handleResult(int requestCode, int resultCode, Intent data);

    public abstract boolean isInstall(Context context);

    public abstract void recycle();
}
