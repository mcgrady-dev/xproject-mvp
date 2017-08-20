package com.mcgrady.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.mcgrady.library.R;

/**
 * ViewPage指示器
 * Created by mcgrady on 2017/3/28.
 */

public class IndicatorView extends View implements ViewPager.OnPageChangeListener {

    // 指示器图标
    private Drawable mIndicator;

    // 指示器图标size，根据宽高确定，选取较大者
    private int mIndicatorSize;

    // 整个指示器控件宽度
    private int mWidth;

    // 图标+空格+padding 的总宽度
    private int mContextWidth;

    // 指示器之间间隔宽度
    private int mIndicatorMargin;

    // 指示器个数（ViewPager item count）
    private int mCount;

    // 当前view选中item
    private int mSelectItem;

    // 指示器根据ViewPager华东的偏移量
    private float mOffset;

    // 指示器是否实时刷新
    private boolean mSmooth;

    private ViewPager.OnPageChangeListener mPageChangeListener;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.IndicatorView);
        initIndicator(typedArray);
    }

    private void initIndicator(TypedArray typedArray) {
        if (typedArray == null)
            return;

        int attr_count = typedArray.getIndexCount();
        for (int i = 0; i < attr_count; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.IndicatorView_indicator_icon) {
                mIndicator = typedArray.getDrawable(attr);

            } else if (attr == R.styleable.IndicatorView_indicator_margin) {
                float defaultMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5,
                        getResources().getDisplayMetrics());
                mIndicatorMargin = (int) typedArray.getDimension(attr, defaultMargin);

            } else if (attr == R.styleable.IndicatorView_indicator_smooth) {
                mSmooth = typedArray.getBoolean(attr, false);

            }
        }

        // 回收
        typedArray.recycle();

        // 处理异常资源图
        mIndicatorSize = Math.max(mIndicator.getIntrinsicWidth(), mIndicator.getIntrinsicHeight());

        // 设置指示器边框
        mIndicator.setBounds(0, 0, mIndicator.getIntrinsicWidth(), mIndicator.getIntrinsicHeight());
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.mPageChangeListener = listener;
    }

    public void setViewPager(ViewPager viewPager) {
        if (viewPager == null)
            return;

        PagerAdapter pagerAdapter = viewPager.getAdapter();
        if (pagerAdapter == null)
            throw new RuntimeException("请看使用说明");

        mCount = pagerAdapter.getCount();
        viewPager.addOnPageChangeListener(this);
        mSelectItem = viewPager.getCurrentItem();

        invalidate();
    }


    private int measureWidth(int widthMeasureSpec){
        int mode = MeasureSpec.getMode(widthMeasureSpec) ;
        int size = MeasureSpec.getSize(widthMeasureSpec) ;
        int width ;
        int desired = getPaddingLeft() + getPaddingRight() + mIndicatorSize*mCount + mIndicatorMargin*(mCount -1) ;
        mContextWidth = desired ;
        if(mode == MeasureSpec.EXACTLY){
            width = Math.max(desired, size)  ;
        }else {
            if(mode == MeasureSpec.AT_MOST){
                width = Math.min(desired,size) ;
            }else {
                width = desired ;
            }
        }
        mWidth = width ;
        return width ;
    }

    private int measureHeight(int heightMeasureSpec){
        int mode = MeasureSpec.getMode(heightMeasureSpec) ;
        int size = MeasureSpec.getSize(heightMeasureSpec) ;
        int height ;
        if(mode == MeasureSpec.EXACTLY){
            height = size ;
        }else {
            int desired = getPaddingTop() + getPaddingBottom() + mIndicatorSize ;
            if(mode == MeasureSpec.AT_MOST){
                height = Math.min(desired,size) ;
            }else {
                height = desired ;
            }
        }

        return height ;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.save();

        /**
         * 绘制指示器总数
         */
        int left = mWidth / 2 - mContextWidth / 2 + getPaddingLeft();
        canvas.translate(left, getPaddingTop());
        for (int i = 0; i < mCount; i++) {

            // 用于获取selector文件（默认状态）
            mIndicator.setState(EMPTY_STATE_SET);

            // 绘制 drawable
            mIndicator.draw(canvas);

            // 每绘制一个指示器，向右移动一次
            canvas.translate(mIndicatorSize + mIndicatorMargin, 0);
        }

        // 恢复画布相关设置
        canvas.restore();

        /**
         * 绘制当前选中指示器
         */
        // 计算绘制的位置
        float leftDraw = mIndicatorMargin * (mSelectItem + mOffset);

        // 平移
        canvas.translate(left, getPaddingTop());
        canvas.translate(leftDraw, 0);

        // 设置选中状态
        mIndicator.setState(SELECTED_STATE_SET);

        // 绘制
        mIndicator.draw(canvas);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mSmooth) {
            mSelectItem = position;
            mOffset = positionOffset;
            invalidate();
        }

        if (mPageChangeListener != null)
            mPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        mSelectItem = position;
        invalidate();

        if (mPageChangeListener != null)
            mPageChangeListener.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mPageChangeListener != null)
            mPageChangeListener.onPageScrollStateChanged(state);
    }
}
