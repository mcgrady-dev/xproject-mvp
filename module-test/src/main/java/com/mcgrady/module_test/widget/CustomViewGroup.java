package com.mcgrady.module_test.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.mcgrady.module_test.activity.CustomViewActivity;

/**
 * Created by mcgrady on 2020/8/5.
 */
public class CustomViewGroup extends RelativeLayout {
    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        String motionEvent = CustomViewActivity.getMotionEvent(ev);
        Log.d("ViewGroup", "dispatchTouchEvent " + motionEvent);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        String motionEvent = CustomViewActivity.getMotionEvent(ev);
        Log.d("ViewGroup", "onInterceptTouchEvent " + motionEvent);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String motionEvent = CustomViewActivity.getMotionEvent(event);
        Log.e("ViewGroup", "onTouchEvent " + motionEvent);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
        }
        return super.onTouchEvent(event);
    }
}
