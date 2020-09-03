package com.mcgrady.module_test.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mcgrady.module_test.R;

/**
 * Created by mcgrady on 2020/7/8.
 */
public class AnimatorActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);

        imageView = findViewById(R.id.iv_bg);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator translationX = ObjectAnimator.ofFloat(imageView, "translationX", 0f, 100f, 0f);
                translationX.setDuration(3000);
                ObjectAnimator translationY = ObjectAnimator.ofFloat(imageView, "translationY", 0f, 100f, 0f);
                translationY.setDuration(3000);

                ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f, 1f);
                alpha.setDuration(3000);

                ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 2f, 1f);
                scaleX.setDuration(3000);
                scaleX.setRepeatCount(2);
                scaleX.setRepeatMode(ValueAnimator.RESTART);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 2f,1f);
                scaleY.setDuration(3000);
                scaleY.setRepeatCount(2);
                scaleY.setRepeatMode(ValueAnimator.RESTART);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(translationX).with(translationY).before(alpha);
                animatorSet.start();

                imageView.scrollBy(300, 600);
                imageView.invalidate();


//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
//                layoutParams.width += 100;
//                layoutParams.leftMargin += 100;
//                imageView.requestLayout();
            }
        });


    }
}
