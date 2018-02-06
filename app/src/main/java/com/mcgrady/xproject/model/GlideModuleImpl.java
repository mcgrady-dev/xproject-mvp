package com.mcgrady.xproject.model;

import android.app.ActivityManager;
import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/2/6
 * @des: 配置Glide
 */

public class GlideModuleImpl extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager != null) {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);

            // 低内存时使用 RGB_565（也是Glide默认使用的设置）
            builder.setDecodeFormat(memoryInfo.lowMemory ?
                    DecodeFormat.PREFER_RGB_565 : DecodeFormat.PREFER_ARGB_8888);
        }
    }
}
