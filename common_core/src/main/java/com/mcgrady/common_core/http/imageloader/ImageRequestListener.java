package com.mcgrady.common_core.http.imageloader;

import android.graphics.drawable.Drawable;

import io.reactivex.annotations.NonNull;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/3/1
 */

public interface ImageRequestListener {

    default boolean onLoadFailed(@NonNull Drawable errorResource) {
        return false;
    }

    void onLoadSuccess(@NonNull Drawable resource);
}
