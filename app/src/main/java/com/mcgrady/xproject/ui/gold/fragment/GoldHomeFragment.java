package com.mcgrady.xproject.ui.gold.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.mcgrady.xproject.R;
import com.mcgrady.xproject.base.BaseFragment;
import com.mcgrady.xproject.contract.GoldHomeContract;
import com.mcgrady.xproject.presenter.GoldHomePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by mcgrady on 2017/8/30.
 * 稀土掘金主页
 */

public class GoldHomeFragment extends BaseFragment<GoldHomePresenter> implements GoldHomeContract.View {

    @BindView(R.id.tab_gold_main)
    TabLayout mTableLayout;
    @BindView(R.id.vp_gold_main)
    ViewPager mViewPager;


    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gold_home;
    }

    @Override
    protected void init() {
        mTableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTableLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

}
