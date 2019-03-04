package com.mcgrady.common_res.utils;

import android.app.Activity;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.mcgrady.common_res.interf.IViewHolderRelease;

import java.util.UUID;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/15
 */

public class ViewUtils {

    /**
     * 配置 RecyclerView
     *
     * @param recyclerView
     * @param layoutManager
     */
    public static void configRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 遍历所有{@link android.support.v7.widget.RecyclerView.ViewHolder},释放他们需要释放的资源
     *
     * @param recyclerView
     */
    public static void releaseAllHolder(RecyclerView recyclerView) {
        if (recyclerView == null) { return; }

        if (recyclerView.getAdapter() instanceof IViewHolderRelease) {
            for (int i = recyclerView.getChildCount() - 1; i >= 0; i--) {
                final View view = recyclerView.getChildAt(i);
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                if (viewHolder instanceof BaseViewHolder) {
                    ((IViewHolderRelease) recyclerView.getAdapter()).onRelease(viewHolder);
                }
            }
        }
    }

    public static int generateViewId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return View.generateViewId();
        } else {
            return UUID.randomUUID().hashCode();
        }
    }

    /**
     * 获取屏幕可用宽度
     * @param activity
     * @return
     */
    public static int getScreenWidthWithoutVirtualBar(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕可用高度
     * @param activity
     * @return
     */
    public static int getScreenHeightWithoutVirtualBar(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}
