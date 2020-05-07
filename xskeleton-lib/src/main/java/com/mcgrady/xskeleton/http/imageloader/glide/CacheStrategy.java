package com.mcgrady.xskeleton.http.imageloader.glide;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by mcgrady on 2020/5/5.
 */
public interface CacheStrategy {
    int ALL = 0;

    int NONE = 1;

    int RESOURCE = 2;

    int DATA = 3;

    int AUTOMATIC = 4;

    @IntDef({ALL, NONE, RESOURCE, DATA, AUTOMATIC})
    @Retention(RetentionPolicy.SOURCE)
    @interface Strategy {
    }
}
