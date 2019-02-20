package com.mcgrady.shop.app.mvp.ui.weiget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.blankj.utilcode.util.ConvertUtils;

/**
 * <p>解决Sku过多时，选择界面铺满全屏的问题</p>
 *
 * @author: mcgrady
 * @date: 2019/2/20
 */

public class SkuMaxHeightScrollView extends ScrollView {

    public SkuMaxHeightScrollView(Context context) {
        super(context);
    }

    public SkuMaxHeightScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            height = h > height ? h : height;
        }

        float heightDp = ConvertUtils.px2dp(height);
        if (heightDp > 220) {
            int maxHeight = ConvertUtils.dp2px(220);
            setMeasuredDimension(width, maxHeight);
        } else if (heightDp < 75) {
            int minHeight = ConvertUtils.px2dp(75);
            setMeasuredDimension(width, minHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
