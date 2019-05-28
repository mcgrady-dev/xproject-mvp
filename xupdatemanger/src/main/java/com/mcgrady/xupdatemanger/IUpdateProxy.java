package com.mcgrady.xupdatemanger;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author: mcgrady
 * @date: 2019-05-28
 */
public interface IUpdateProxy<T extends Object> {

    Context getContext();

    void update();

    void checkVersion();

    void onCheckBefore();

    void onCheckAfter();

    T parseJson(@NonNull String json) throws Exception;
}
