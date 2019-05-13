package com.mcgrady.common_core.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * Created by mcgrady on 2019/5/13.
 */
public class PriceUtils {

    /**
     * 金额 格式化
     */
    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("###,###,###,##0.00");

    public static String formatPrice(String value) {
        return formatPrice(TextUtils.isEmpty(value) ? 0 : Double.parseDouble(value));
    }

    public static String formatPrice(double value) {
        return PRICE_FORMAT.format(value);
    }
}
