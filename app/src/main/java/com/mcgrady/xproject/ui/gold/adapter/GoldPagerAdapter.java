package com.mcgrady.xproject.ui.gold.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mcgrady.xproject.ui.gold.fragment.GoldPagerFragment;

import java.util.List;

/**
 * Created by mcgrady on 2017/11/29.
 */

public class GoldPagerAdapter extends FragmentStatePagerAdapter {

    List<GoldPagerFragment> fragments;

    public GoldPagerAdapter(FragmentManager fm, List<GoldPagerFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }
}
