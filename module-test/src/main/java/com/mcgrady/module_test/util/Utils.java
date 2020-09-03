package com.mcgrady.module_test.util;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by mcgrady on 2020/8/16.
 */
public class Utils {

    public static float dp2px(int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, Resources.getSystem().getDisplayMetrics());
    }

    public static float sp2px(int value) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (value * fontScale + 0.5f);
    }
}
