package com.mcgrady.common_core.utils;

import android.content.Context;
import android.os.Vibrator;

/**
 * <p>震动工具类。</p>
 *
 * @author moguangjian
 * @date 2018/1/8
 */
public class VibratorUtils {

    /**
     * 振动器
     *
     * @param context
     * @param duration
     */
    public static void vibrate(Context context, long duration) {
        try {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = {
                    0, duration
            };
            vibrator.vibrate(pattern, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
