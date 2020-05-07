package com.mcgrady.common_core.utils;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

/**
 * <p>检查View重复点击工具</p>
 *
 * @author: mcgrady
 * @date: 2018/9/3f
 */

public class MultiClickUtils {

    private static final long CLICK_INTERVAL = 500L;
    private static long lastClickTime = 0L;
    private static @IdRes int lastClickViewId = -1;

    /**
     * 重置点击过滤器, 在每次用代码模拟View点击事件的时候, 要先reset一下, 以防被过滤掉
     */
    public static void resetMultiClick() {
        lastClickTime = 0L;
    }

    public static boolean isMultiClick() {
        return isMultiClick(CLICK_INTERVAL);
    }

    /**
     *
     * @param limitTime
     * @return
     * @see SingleClick
     */
    @Deprecated
    public static boolean isMultiClick(long limitTime) {
        long time = System.currentTimeMillis();
        if ((time - lastClickTime) > limitTime) {
            lastClickTime = time;
            return false;
        }
        lastClickTime = time;
        return true;
    }

    public static boolean isMultiClick(@NonNull View view, long limitTime) {
        int viewId = view.getId();
        long time = System.currentTimeMillis();
        long timeInterval = Math.abs(time - lastClickTime);
        if (timeInterval < limitTime && viewId == lastClickTime) {
            return true;
        } else {
            lastClickTime = time;
            lastClickViewId = viewId;
            return false;
        }
    }
}
