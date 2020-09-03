package com.mcgrady.module_test.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.mcgrady.module_test.activity.CustomViewActivity;

/**
 * Created by mcgrady on 2020/8/3.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CustomizeView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path();

    private float lastX, lastY;
    private Scroller scroller;

    public CustomizeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        scroller = new Scroller(getContext());

        // 使用 path 对图形进行描述（这段描述代码不必看懂）
        path.addArc(200, 200, 400, 400, -225, 225);
        path.arcTo(400, 200, 600, 400, -180, 225, false);
        path.lineTo(400, 542);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        paint.setColor(Color.LTGRAY);
//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawOval(Utils.dp2px(10), Utils.dp2px(10), getWidth() - Utils.dp2px(10), getHeight() - Utils.dp2px(10), paint);
//        paint.setColor(ContextCompat.getColor(getContext(), android.R.color.holo_blue_light));
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, Utils.dp2px(150), paint);
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(Utils.dp2px(10));
//        canvas.drawRect(getWidth() / 2 - Utils.dp2px(320) / 2, getHeight() / 2 - Utils.dp2px(320) / 2, getWidth() / 2 + Utils.dp2px(320) / 2, getHeight() / 2 + Utils.dp2px(320) / 2, paint);
//        paint.setStrokeWidth(Utils.dp2px(40));
//        paint.setColor(Color.YELLOW);
//        paint.setStrokeCap(Paint.Cap.ROUND);
//        canvas.drawPoint(getWidth() / 2, getHeight() / 2, paint);
//        paint.setStrokeWidth(Utils.dp2px(20));
//        paint.setColor(Color.GREEN);
//        paint.setStrokeCap(Paint.Cap.BUTT);
//        float[] points = {getWidth() / 2 - Utils.dp2px(75), getHeight() / 2 - Utils.dp2px(75),
//                getWidth() / 2 + Utils.dp2px(75), getHeight() / 2 - Utils.dp2px(75),
//                getWidth() / 2 - Utils.dp2px(75), getHeight() / 2 + Utils.dp2px(75),
//                getWidth() / 2 + Utils.dp2px(75), getHeight() / 2 + Utils.dp2px(75)};
//        canvas.drawPoints(points, paint);
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);
//        paint.setColor(Resources.getSystem().getColor(android.R.color.holo_orange_light));
//        paint.setStrokeWidth(Utils.dp2px(5));
//        float[] line_points = {0, getHeight() / 2, getWidth(), getHeight() / 2,
//                getWidth() / 2, 0, getWidth() / 2, getHeight(),
//                0, 0, getWidth(), getHeight(),
//                getWidth(), 0, 0, getHeight()};
//        canvas.drawLines(line_points, paint);
//
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Resources.getSystem().getColor(android.R.color.holo_purple));
//        canvas.drawRoundRect(getWidth() / 2 - Utils.dp2px(150),
//                Utils.dp2px(20),
//                getWidth() / 2 + Utils.dp2px(150),
//                Utils.dp2px(75) + Utils.dp2px(20),
//                30, 30,
//                paint);
//
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Resources.getSystem().getColor(android.R.color.holo_red_light));
//        canvas.drawPath(path, paint);

        


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        String motionEvent = CustomViewActivity.getMotionEvent(event);
        Log.d("View", "dispatchTouchEvent " + motionEvent);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String motionEvent = CustomViewActivity.getMotionEvent(event);
        Log.e("View", "onTouchEvent " + motionEvent);
        return super.onTouchEvent(event);
//        final float x = event.getX();
//        final float y = event.getY();
//        final int action = event.getAction();
//
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                lastX = x;
//                lastY = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int offsetX = (int) (x - lastX);
//                int offsetY = (int) (y - lastY);
//
////                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
//
////                offsetLeftAndRight(offsetX);
////                offsetTopAndBottom(offsetY);
//
////                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
////                layoutParams.leftMargin = getLeft() + offsetX;
////                layoutParams.topMargin = getTop() + offsetY;
////                setLayoutParams(layoutParams);
//
//                ((View)getParent()).scrollBy(-offsetX, -offsetY);
//                break;
//        }
//
//        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int deltaX = destX - scrollX;
        int deltaY = destY - scrollY;
        scroller.startScroll(scrollX, 0, deltaX, deltaY, 2000);
        invalidate();
    }
}
