package com.mcgrady.xproject.ui.gold.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.mcgrady.xproject.R;
import com.mcgrady.xproject.app.Constants;
import com.mcgrady.xproject.base.BaseFragment;
import com.mcgrady.xproject.contract.GoldHomeContract;
import com.mcgrady.xproject.model.bean.GoldManagerBean;
import com.mcgrady.xproject.model.bean.GoldManagerItemBean;
import com.mcgrady.xproject.presenter.GoldHomePresenter;
import com.mcgrady.xproject.ui.gold.adapter.GoldPagerAdapter;

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

    public static String[] typeStr = {"Android", "iOS", "前端", "后端", "设计", "产品", "阅读", "工具资源"};
    public static String[] type = {"android", "ios", "frontend", "backend", "design", "product", "article", "freebie"};

    List<GoldPagerFragment> fragments = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gold_home;
    }

    @Override
    protected void init() {
        mTableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTableLayout.setupWithViewPager(mViewPager);
        mPresenter.initManagerList();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void updateTab(List<GoldManagerItemBean> list) {

        fragments.clear();
        mTableLayout.removeAllTabs();
        for (GoldManagerItemBean item : list) {
            if (item.isSelect()) {
                GoldPagerFragment fragment = new GoldPagerFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.IT_GOLD_TYPE, type[item.getIndex()]);
                bundle.putString(Constants.IT_GOLD_TYPE_STR, typeStr[item.getIndex()]);
                mTableLayout.addTab(mTableLayout.newTab().setText(typeStr[item.getIndex()]));
                fragments.add(fragment);
            }
        }

        GoldPagerAdapter adapter = new GoldPagerAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        for (GoldManagerItemBean item : list) {
            if (item.isSelect()) {
                mTableLayout.getTabAt(currentIndex++).setText(typeStr[item.getIndex()]);
            }
        }
        currentIndex = 0;

    }

    @Override
    public void jumpToManager(GoldManagerBean bean) {
    }
}
