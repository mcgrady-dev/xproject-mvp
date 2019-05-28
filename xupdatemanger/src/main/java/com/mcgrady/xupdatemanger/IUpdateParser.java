package com.mcgrady.xupdatemanger;

import android.support.annotation.NonNull;

/**
 * @author: mcgrady
 * @date: 2019-05-28
 */
public interface IUpdateParser<T extends Object> {

    T parseJson(@NonNull String json) throws Exception;
}
