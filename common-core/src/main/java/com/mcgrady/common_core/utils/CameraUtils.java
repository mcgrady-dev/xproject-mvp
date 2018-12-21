package com.mcgrady.common_core.utils;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;

import com.blankj.utilcode.util.DeviceUtils;

import java.lang.reflect.Field;

/**
 * <p>摄像头相关工具类</p>
 *
 * @author: mcgrady
 * @date: 2018/11/29
 */

public class CameraUtils {

    /**
     * 测试当前摄像头能否被使用
     *
     * @return
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();

            if ("vivo".equals(DeviceUtils.getManufacturer())
                    || "VIVO".equals(DeviceUtils.getManufacturer())) {
                canUse = isVivoCameraCanUse(mCamera);
            } else {
                Camera.Parameters mParameters = mCamera.getParameters(); //针对魅族手机
                mCamera.setParameters(mParameters);
            }
        } catch (Exception e) {
            canUse = false;
        }
        if (canUse) {
            mCamera.release();
            mCamera = null;
        }
        return canUse;
    }

    public static boolean isVivoCameraCanUse(Camera camera) {
        try {
            Field field = camera.getClass().getDeclaredField("mHasPermission");
            field.setAccessible(true);
            return (boolean) field.get(camera);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return true;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return true;
        } catch (Exception e) {
            //如果camera中没有mHsPermission是会抛出异常的，但是不能判断为没有权限，所以返回true
            return true;
        }
    }

    /**
     * 相机权限设置
     * 跳转至设置页面
     */
    public static void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }
}
