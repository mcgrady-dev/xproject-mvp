package com.mcgrady.module_test.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import com.mcgrady.module_test.activity.CustomViewActivity;

/**
 * Created by mcgrady on 2020/8/3.
 */
public class CustomizeView extends View {

    private float lastX, lastY;
    private Scroller scroller;

    public CustomizeView(Context context) {
        super(context);
        init();
    }

    public CustomizeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomizeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        scroller = new Scroller(getContext());
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
            ((View)getParent()).scrollTo(scroller.getCurrX(), scroller.getCurrY());
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
