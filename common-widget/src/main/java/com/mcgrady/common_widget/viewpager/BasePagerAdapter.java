package com.mcgrady.common_widget.viewpager;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcgrady on 2020/6/1.
 *
 * 对PagerAdapter进行封装，通过{@link #getItemPosition(Object)}返回正确的值，达到支持ViewPager数据刷新时，
 * 视图按需创建、删除以及刷新的目的；
 *
 * @param <Item> Item的数据类型
 */
public abstract class BasePagerAdapter<Item> extends PagerAdapter {

    private static final String TAG = BasePagerAdapter.class.getSimpleName();

    // 数据集合
    private List<Item> mItems;
    // 数据-视图 映射关系对象集合
    private List<ViewItemHolder> mViewItemHolders = new ArrayList<>();
    // 是否处于数据刷新中
    private boolean mDataSetChanging;

    public BasePagerAdapter(@NonNull List<Item> items) {
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        ViewItemHolder viewItemHolder = (ViewItemHolder) object;
        return viewItemHolder.mItemView == view;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d(TAG,"instantiateItem() called with: position = [" + position + "]");
        Item item = mItems.get(position);
        View itemView = instantiateItemView(container, item, position);
        bindItemView(itemView, item, position, true);
        container.addView(itemView);
        ViewItemHolder viewItemHolder = new ViewItemHolder(item, itemView, position);
        mViewItemHolders.add(viewItemHolder);
        return viewItemHolder;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d(TAG, "destroyItem() called with: position = [" + position + "]");
        ViewItemHolder viewItemHolder = (ViewItemHolder) object;
        container.removeView(viewItemHolder.mItemView);
        mViewItemHolders.remove(viewItemHolder);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        ViewItemHolder viewItemHolder = (ViewItemHolder) object;
        Item item = viewItemHolder.mItem;
        // 判断当前内存中页面数据是否还存在于刷新后的数据集合中，不存在返回POSITION_NONE进行移除
        int newPos = mItems.indexOf(item);
        int itemPosition = newPos == -1 ? POSITION_NONE : newPos;
        int oldPos = viewItemHolder.mPosition;
        Log.d(TAG, "getItemPosition: oldPos=" + oldPos + ",newPos=" + newPos);
        if (itemPosition >= 0) {
            // 数据索引发生改变
            if (oldPos != itemPosition) {
                // 更新索引位置
                viewItemHolder.mPosition = itemPosition;
            }
            // 当前页面重新绑定数据，以便于刷新视图内容
            bindItemView(viewItemHolder.mItemView, item, itemPosition, false);
        }
        return itemPosition;
    }

    @CallSuper
    @Override
    public void notifyDataSetChanged() {
        mDataSetChanging = true;
        super.notifyDataSetChanged();
        mDataSetChanging = false;
    }

    // 负责持有视图、数据的对应关系
    private class ViewItemHolder {
        private Item mItem;
        private View mItemView;
        private int mPosition;

        ViewItemHolder(Item item, View itemView, int position) {
            mItem = item;
            mItemView = itemView;
            mPosition = position;
        }
    }

    /**
     * 创建视图
     *
     * @param container 容器，即ViewPager
     * @param item      数据
     * @param position  索引
     * @return View
     */
    @NonNull
    protected abstract View instantiateItemView(@NonNull ViewGroup container, Item item, int position);

    /**
     * 给ItemView绑定数据（创建视图以及数据刷新时都会调用）
     *
     * @param itemView 视图
     * @param item     数据
     * @param position 索引
     * @param first    是否为首次绑定调用，视图创建后首次绑定该值为true；数据刷新时调用为false
     */
    protected abstract void bindItemView(@NonNull View itemView, Item item, int position, boolean first);

    /**
     * 获取页面视图对应的数据索引
     *
     * @param page 页面视图
     * @return 未找到返回-1
     */
    public int getPageViewPosition(View page) {
        for (ViewItemHolder viewItemHolder : mViewItemHolders) {
            if (viewItemHolder.mItemView == page) {
                return viewItemHolder.mPosition;
            }
        }
        return -1;
    }

    /**
     * 数据是否正在刷新中，即是否处于{@link #notifyDataSetChanged()}到{@link ViewPager#dataSetChanged()}执行过程
     *
     * @return 刷新中返回true
     */
    public boolean isDataSetChanging() {
        return mDataSetChanging;
    }
}
