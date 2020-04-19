package com.mcgrady.common_res.utils;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by mcgrady on 2019-10-15.
 */
public class PressedMode {

    @IntDef({ALPHA, DARK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {}

    /**
     * 按下改变透明度
     */
    public static final int ALPHA = 0;
    /**
     * 按下增加黑色遮罩
     */
    public static final int DARK = 1;
}
