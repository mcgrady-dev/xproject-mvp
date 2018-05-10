package com.mcgrady.core.account;

import android.accounts.Account;
import android.content.Context;

import com.mcgrady.core.utils.AppContext;

/**
 * <p>账号管理类</p>
 *
 * @author: mcgrady
 * @date: 2018/5/10
 */

public enum AccountManager {

    INSTANCE;

    private final AuthPreferences authPreferences;
    private Account mCurrentAccount;
    private Context mContext;

    AccountManager() {
        mContext = AppContext.get();
        authPreferences = new AuthPreferences(AppContext.get());
    }
}
