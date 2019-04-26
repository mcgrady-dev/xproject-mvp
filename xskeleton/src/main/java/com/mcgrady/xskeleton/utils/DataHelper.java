package com.mcgrady.xskeleton.utils;

import android.content.Context;
import android.os.Environment;

import com.blankj.utilcode.util.FileUtils;

import java.io.File;

/**
 * Created by mcgrady on 2019/4/26.
 */
public class DataHelper {

    /**
     * 获取缓存目录
     */
    public static File getCacheFile(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = null;
            file = context.getExternalCacheDir();//获取系统管理的sd卡缓存文件
            if (file == null) {//如果获取的文件为空,就使用自己定义的缓存文件夹做缓存路径
                file = new File(getCacheFilePath(context));
                FileUtils.createOrExistsDir(file);
            }
            return file;
        } else {
            return context.getCacheDir();
        }
    }

    /**
     * 获取自定义缓存文件地址
     *
     * @param context
     * @return
     */
    public static String getCacheFilePath(Context context) {
        String packageName = context.getPackageName();
        return "/mnt/sdcard/" + packageName;
    }
}
