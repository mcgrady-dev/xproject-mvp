package com.mcgrady.xupdatemanager.interf;

import android.support.annotation.NonNull;

/**
 * @author: mcgrady
 * @date: 2019-05-28
 */
public interface IUpdateChecker {

    void checkVersion();

    void processResult(@NonNull String result, @NonNull IUpdateProxy updateProxy);

    void onCheckBefore();

    void onCheckAfter();
}
