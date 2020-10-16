package com.mcgrady.module_test.activity;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mcgrady.module_test.R;
import com.mcgrady.module_test.util.SelectorDrawable;

public class TabActivity extends AppCompatActivity {

    private TextView tvLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        tvLeft = findViewById(R.id.tv_left);

//        SelectorDrawable selectorDrawable = new SelectorDrawable.Builder()
//                .setDefault(new ColorDrawable(0x00000000))
//                .setFocused(new ColorDrawable(0x0C000000))
//                .setPressed(new ColorDrawable(0x0C000000))
//                .build();

        SelectorDrawable selectorDrawable = new SelectorDrawable.Builder()
                .setDefault(new ColorDrawable(0x00000000))
                .setFocused(new ColorDrawable(0x22000000))
                .setPressed(new ColorDrawable(0x22000000))
                .build();

        tvLeft.setEnabled(true);
        tvLeft.setClickable(true);
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvLeft.setBackgroundDrawable(selectorDrawable);

        TypedValue typedValue = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Drawable drawable = getResources().getDrawable(typedValue.resourceId, getTheme());
                tvLeft.setBackground(drawable);
            }
        }
    }
}