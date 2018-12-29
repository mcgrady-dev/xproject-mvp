package com.mcgrady.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mcgrady.main.mvp.ui.fragment.xhs.XhsAnimImageFristFragment;
import com.mcgrady.main.mvp.ui.fragment.xhs.XhsAnimImageSecondFragment;
import com.mcgrady.main.mvp.ui.fragment.xhs.XhsAnimImageThridFragment;

import java.util.ArrayList;

/**
 * 总共上层有3页动画,写死在adapter
 */
public class XhsImageFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> mFragments;
    XhsAnimImageFristFragment fristFragment;
    XhsAnimImageSecondFragment secondFrag;
    XhsAnimImageThridFragment thridFrag;

    public Fragment getFragement(int position) {
        switch (position) {
            case 0:
                return fristFragment;
            case 1:
                return secondFrag;
            case 2:
                return thridFrag;
            default:
                break;
        }
        return null;
    }

    public XhsImageFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
        if(mFragments == null){
            mFragments = new ArrayList<>();
            fristFragment = new XhsAnimImageFristFragment();
            mFragments.add(fristFragment);
            secondFrag = new XhsAnimImageSecondFragment();
            mFragments.add(secondFrag);
            thridFrag = new XhsAnimImageThridFragment();
            mFragments.add(thridFrag);
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fristFragment;
            case 1:
                return secondFrag;
            case 2:
                return thridFrag;
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
