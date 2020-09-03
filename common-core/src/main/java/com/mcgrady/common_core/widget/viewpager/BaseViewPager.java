package com.mcgrady.common_core.widget.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.view.AbsSavedState;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mcgrady.common_core.R;


/**
 * Created by mcgrady on 2020/6/1.
 *
 * 自定义ViewPager，在ViewPager原有基础上，提供拓展功能和修复问题。<br>
 * 拓展功能：
 * <ol>
 * <li>提供自定义属性，快速实现一屏多页的功能；</li>
 * <li>支持width、childWidth变化后，滚动位置修正；</li>
 * </ol>
 * 修复：
 * <ol>
 * <li>PagerTransformer在设置paddingLeft和paddingRight时返回的position偏移问题；</li>
 * <li>PagerTransformer在数据刷新时返回的position错误问题；</li>
 * <li>ViewPager动态修改width、paddingLeft、paddingRight后滚动位置偏差问题；</li>
 * <li>ViewPager动态修改pageMargin后滚动位置偏差问题；</li>
 * </ol>
 * <font color=red>注意：如果使用比例属性以及边距来控制page大小，请勿设置padding。因为内部会通过计算合适的padding来
 * 控制页面的大小</font>
 */
public class BaseViewPager extends ViewPager {

    // 处理尺寸变化
    private BaseViewPagerSupport.SizeChangeHandler mSizeChangeHandler;
    // 多Page支持插件
    private MultiPagePlugin mMultiPagePlugin;

    public BaseViewPager(@NonNull Context context) {
        this(context, null);
    }

    public BaseViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initFromAttributes(context, attrs);
        mSizeChangeHandler = new BaseViewPagerSupport.SizeChangeHandler();
    }

    private void initFromAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseViewPager);
        float pageHeightWidthRatio = typedArray.getFloat(R.styleable.BaseViewPager_vp_pageHeightWidthRatio, 0);
        if (pageHeightWidthRatio < 0) {
            pageHeightWidthRatio = 0;
        }
        int pageHorizontalMinMargin = typedArray.getDimensionPixelSize(R.styleable.BaseViewPager_vp_pageHorizontalMinMargin, 0);
        int pageVerticalMinMargin = typedArray.getDimensionPixelSize(R.styleable.BaseViewPager_vp_pageVerticalMinMargin, 0);
        typedArray.recycle();

        // init MultiPagePlugin
        mMultiPagePlugin = new MultiPagePlugin.Builder(this)
                .pageHeightWidthRatio(pageHeightWidthRatio)
                .pageHorizontalMinMargin(pageHorizontalMinMargin)
                .pageVerticalMinMargin(pageVerticalMinMargin)
                .build();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        mMultiPagePlugin.determinePageSize(width, height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int width = r - l;
        // 响应宽度变化以便修正滚动位置
        mSizeChangeHandler.onSizeChange(this, width);
    }

    /**
     * 源码有bug会导致滚动位置偏移，请调用{@link #setBasePageMargin(int)}
     *
     * @param marginPixels Distance between adjacent pages in pixels
     */
    @Deprecated
    @Override
    public void setPageMargin(int marginPixels) {
        super.setPageMargin(marginPixels);
    }

    /**
     * Set the margin between pages.（修正滚动位置）
     *
     * @param marginPixels Distance between adjacent pages in pixels
     * @see #getPageMargin()
     * @see #setPageMarginDrawable(Drawable)
     * @see #setPageMarginDrawable(int)
     */
    public void setBasePageMargin(int marginPixels) {
        BaseViewPagerSupport.setPageMargin(this, marginPixels);
    }

    /**
     * PageTransformer源码返回的position未考虑padding，以及刷新后position错误，导致动画有问题，请调用
     * {@link #setPageTransformer(boolean, PageTransformer)}
     *
     * @param reverseDrawingOrder true if the supplied PageTransformer requires page views
     *                            to be drawn from last to first instead of first to last.
     * @param transformer         PageTransformer that will modify each page's animation properties
     */
    @Deprecated
    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, @Nullable PageTransformer transformer) {
        super.setPageTransformer(reverseDrawingOrder, transformer);
    }

    /**
     * PageTransformer源码返回的position未考虑padding，以及刷新后position错误，导致动画有问题，请调用
     * {@link #setBasePageTransformer(boolean, BasePageTransformer, int)}
     *
     * @param reverseDrawingOrder true if the supplied PageTransformer requires page views
     *                            to be drawn from last to first instead of first to last.
     * @param transformer         PageTransformer that will modify each page's animation properties
     * @param pageLayerType       View layer type that should be used for ViewPager pages. It should be
     *                            either {@link View#LAYER_TYPE_HARDWARE},
     *                            {@link View#LAYER_TYPE_SOFTWARE}, or
     *                            {@link View#LAYER_TYPE_NONE}.
     */
    @Deprecated
    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, @Nullable PageTransformer transformer, int pageLayerType) {
        super.setPageTransformer(reverseDrawingOrder, transformer, pageLayerType);
    }

    /**
     * 修正了返回position偏移以及数据刷新时错误问题
     *
     * @param reverseDrawingOrder true if the supplied PageTransformer requires page views
     *                            to be drawn from last to first instead of first to last.
     * @param transformer         PageTransformer that will modify each page's animation properties
     */
    public void setBasePageTransformer(boolean reverseDrawingOrder, @Nullable BasePageTransformer transformer) {
        super.setPageTransformer(reverseDrawingOrder, transformer);
    }

    /**
     * 修正了返回position偏移以及数据刷新时错误问题
     *
     * @param reverseDrawingOrder true if the supplied PageTransformer requires page views
     *                            to be drawn from last to first instead of first to last.
     * @param transformer         PageTransformer that will modify each page's animation properties
     * @param pageLayerType       View layer type that should be used for ViewPager pages. It should be
     *                            either {@link View#LAYER_TYPE_HARDWARE},
     *                            {@link View#LAYER_TYPE_SOFTWARE}, or
     *                            {@link View#LAYER_TYPE_NONE}.
     */
    public void setBasePageTransformer(boolean reverseDrawingOrder, @Nullable BasePageTransformer transformer, int pageLayerType) {
        super.setPageTransformer(reverseDrawingOrder, transformer, pageLayerType);
    }

    /**
     * adapter传入{@link BasePagerAdapter}实现；或者直接使用
     * {@link #setBaseAdapter(BasePagerAdapter)}以便能够按需更新数据；
     *
     * @param adapter Adapter to use
     */
    @Override
    public void setAdapter(@Nullable PagerAdapter adapter) {
        super.setAdapter(adapter);
    }

    /**
     * Set a PagerAdapter that will supply views for this pager as needed.
     * （{@link BasePagerAdapter}）
     *
     * @param adapter Adapter to use
     */
    public void setBaseAdapter(@NonNull BasePagerAdapter adapter) {
        setAdapter(adapter);
    }

    /**
     * 获取当前page比例
     *
     * @return page比例
     */
    public float getPageHeightWidthRatio() {
        return mMultiPagePlugin.getPageHeightWidthRatio();
    }

    /**
     * 设置当前page比例
     *
     * @param pageHeightWidthRatio page比例，如果小于0会被当做0处理
     */
    public void setPageHeightWidthRatio(float pageHeightWidthRatio) {
        mMultiPagePlugin.setPageHeightWidthRatio(pageHeightWidthRatio);
    }

    /**
     * 获取page水平最小间距
     *
     * @return 水平最小间距
     */
    public int getPageHorizontalMinMargin() {
        return mMultiPagePlugin.getPageHorizontalMinMargin();
    }

    /**
     * 设置page水平最小间距
     *
     * @param pageHorizontalMinMargin 水平最小间距
     */
    public void setPageHorizontalMinMargin(int pageHorizontalMinMargin) {
        mMultiPagePlugin.setPageHorizontalMinMargin(pageHorizontalMinMargin);
    }

    /**
     * 获取page垂直最小间距
     *
     * @return 垂直最小间距
     */
    public int getPageVerticalMinMargin() {
        return mMultiPagePlugin.getPageVerticalMinMargin();
    }

    /**
     * 设置page垂直最小间距
     *
     * @param pageVerticalMinMargin 垂直最小间距
     */
    public void setPageVerticalMinMargin(int pageVerticalMinMargin) {
        mMultiPagePlugin.setPageVerticalMinMargin(pageVerticalMinMargin);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.mPageHeightWidthRatio = mMultiPagePlugin.getPageHeightWidthRatio();
        ss.mPageHorizontalMinMargin = mMultiPagePlugin.getPageHorizontalMinMargin();
        ss.mPageVerticalMinMargin = mMultiPagePlugin.getPageVerticalMinMargin();
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        mMultiPagePlugin.setPageHeightWidthRatio(ss.mPageHeightWidthRatio);
        mMultiPagePlugin.setPageHorizontalMinMargin(ss.mPageHorizontalMinMargin);
        mMultiPagePlugin.setPageVerticalMinMargin(ss.mPageVerticalMinMargin);
    }

    /**
     * 存储数据恢复状态
     */
    private static class SavedState extends AbsSavedState {

        float mPageHeightWidthRatio;
        int mPageHorizontalMinMargin;
        int mPageVerticalMinMargin;

        SavedState(@NonNull Parcelable superState) {
            super(superState);
        }

        SavedState(@NonNull Parcel source, @Nullable ClassLoader loader) {
            super(source, loader);
            mPageHeightWidthRatio = source.readFloat();
            mPageHorizontalMinMargin = source.readInt();
            mPageVerticalMinMargin = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(mPageHeightWidthRatio);
            out.writeInt(mPageHorizontalMinMargin);
            out.writeInt(mPageVerticalMinMargin);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in, null);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
