package com.mcgrady.module_test.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.mcgrady.module_test.util.Utils;

/**
 * Created by mcgrady on 2020/8/24.
 */
public class TagView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public TagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setColor(Color.BLACK);
        paint.setTextSize(Utils.sp2px(16));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        resolveSize(width, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText("XXXX", 100, paint.getFontMetrics().descent, paint);
    }
}
