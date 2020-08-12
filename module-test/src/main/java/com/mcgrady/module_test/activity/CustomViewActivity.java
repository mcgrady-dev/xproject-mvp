package com.mcgrady.module_test.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.mcgrady.module_test.R;
import com.mcgrady.module_test.widget.CustomViewGroup;
import com.mcgrady.module_test.widget.CustomizeView;

public class CustomViewActivity extends AppCompatActivity {

    private CustomizeView customizeView;
    private CustomViewGroup customViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        customizeView = findViewById(R.id.customview);
        customViewGroup = findViewById(R.id.customviewgroup);

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
//        animation.setDuration(1000);
//        customizeView.setAnimation(animation);

//        ObjectAnimator.ofFloat(customizeView, "translationX", 0, 300)
//                .setDuration(1000)
//                .start();

//        customizeView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("CustomView", "onClick");
//                customizeView.smoothScrollTo(-300, -300);
//            }
//        });

//        customViewGroup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("CustomView", "onClick");
//            }
//        });


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        String motionEvent = getMotionEvent(ev);
        Log.d("Activity", "dispatchTouchEvent " + motionEvent);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String motionEvent = getMotionEvent(event);
        Log.e("Activity", "onTouchEvent " + motionEvent);
        return super.onTouchEvent(event);
    }

    public static String getMotionEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
        }

        return "";
    }
}