package com.mcgrady.common_res.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * Created by mcgrady on 2019/5/13.
 */
public class PriceUtils {

    private static final String PATTERN_PRICE_DEFAULT = "###,###,###,##0.00";
    private static final String PATTERN_PRICE_INTEGER = "###,###,###,##0";

    private static final DecimalFormat PRICE_FORMAT_DEFAULT = new DecimalFormat(PATTERN_PRICE_DEFAULT);
    private static final DecimalFormat PRICE_FORMAT_INTEGER = new DecimalFormat(PATTERN_PRICE_INTEGER);

    public static String formatPrice(String value) {
        return formatPrice(TextUtils.isEmpty(value) ? 0 : Double.parseDouble(value));
    }

    public static String formatPrice(double value) {
        return PRICE_FORMAT_DEFAULT.format(value);
    }
}
