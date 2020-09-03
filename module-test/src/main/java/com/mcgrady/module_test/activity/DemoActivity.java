package com.mcgrady.module_test.activity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mcgrady.module_test.R;

public class DemoActivity extends AppCompatActivity {

    FrameLayout frameLayout1, frameLayout2;
    Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        frameLayout1 = findViewById(R.id.fl1);
        frameLayout2 = findViewById(R.id.fl2);
        button1 = findViewById(R.id.btn1);
        button2 = findViewById(R.id.btn2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout1.setVisibility(View.GONE);
                frameLayout2.setVisibility(View.VISIBLE);
                Toast.makeText(DemoActivity.this, "button1", Toast.LENGTH_LONG).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout2.setVisibility(View.GONE);
                frameLayout1.setVisibility(View.VISIBLE);
                Toast.makeText(DemoActivity.this, "button2", Toast.LENGTH_LONG).show();
            }
        });


        frameLayout2.computeScroll();

        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.clear();
        velocityTracker.recycle();

        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }
}