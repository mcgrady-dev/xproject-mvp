package com.mcgrady.common_core.utils;

import android.util.Log;

/**
 * Created by mcgrady on 3/10/21.
 */
public class MemoryUtils {

    public int maxMemory() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        Log.d("TAG", "Max memory is " + maxMemory + "KB");
        return maxMemory;

    }
}
