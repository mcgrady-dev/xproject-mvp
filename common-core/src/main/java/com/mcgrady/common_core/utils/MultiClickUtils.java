package com.mcgrady.common_core.utils;

/**
 * <p>检查View重复点击工具</p>
 *
 * @author: mcgrady
 * @date: 2018/9/3
 */

public class MultiClickUtils {

    private static final long CLICK_INTERVAL = 500L;
    private static long lastClickTime = 0L;

    /**
     * 重置点击过滤器, 在每次用代码模拟View点击事件的时候, 要先reset一下, 以防被过滤掉
     */
    public static void resetMultiClick() {
        lastClickTime = 0L;
    }

    public static boolean isMultiClick() {
        return isMultiClick(CLICK_INTERVAL);
    }

    public static boolean isMultiClick(long limitTime) {
        long time = System.currentTimeMillis();
        if ((time - lastClickTime) > limitTime) {
            lastClickTime = time;
            return false;
        }
        lastClickTime = time;
        return true;
    }
}
