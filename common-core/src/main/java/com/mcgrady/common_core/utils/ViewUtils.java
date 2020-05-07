package com.mcgrady.common_core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.snackbar.Snackbar;
import com.hjq.toast.ToastUtils;

import io.reactivex.Completable;

import static com.mcgrady.xskeleton.integration.Platform.DEPENDENCY_SUPPORT_DESIGN;

/**
 * Created by mcgrady on 2019-08-19.
 */
public class ViewUtils {

    /**
     * 使用 {@link Snackbar} 显示文本消息
     * @param message
     * @param isLong
     */
    public static void showSnackbar(String message, boolean isLong) {
        if (ActivityUtils.getTopActivity() == null) {
            LogUtils.w("top activity == null when showSnackbar");
            return;
        }

        Completable.fromAction(() -> {
            if (DEPENDENCY_SUPPORT_DESIGN) {
                Activity activity = ActivityUtils.getTopActivity();
                View view = activity.getWindow().getDecorView().findViewById(android.R.id.content);
                Snackbar.make(view, message, isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
            } else {
                ToastUtils.show(message);
            }
        });
    }

    /**
     * 获得资源
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * findview
     *
     * @param view
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(Context context, View view, String viewName) {
        int id = getResources(context).getIdentifier(viewName, "id", context.getPackageName());
        T v = (T) view.findViewById(id);
        return v;
    }

    /**
     * findview
     *
     * @param activity
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(Context context, Activity activity, String viewName) {
        int id = getResources(context).getIdentifier(viewName, "id", context.getPackageName());
        T v = (T) activity.findViewById(id);
        return v;
    }
}
