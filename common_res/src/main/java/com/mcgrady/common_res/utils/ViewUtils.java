package com.mcgrady.common_res.utils;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.mcgrady.common_res.interf.IViewHolderRelease;

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
}
