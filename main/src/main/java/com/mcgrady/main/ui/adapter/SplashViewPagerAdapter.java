package com.mcgrady.main.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * <p>引导页适配器</p>
 *
 * @author: mcgrady
 * @date: 2018/5/16
 */

public class SplashViewPagerAdapter extends PagerAdapter {

    int[] guides;

    public SplashViewPagerAdapter(int[] guides) {
        this.guides = guides;
    }

    @Override
    public int getCount() {
        return guides.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(guides[position]);
        container.addView(imageView);

        return imageView;
    }
}
