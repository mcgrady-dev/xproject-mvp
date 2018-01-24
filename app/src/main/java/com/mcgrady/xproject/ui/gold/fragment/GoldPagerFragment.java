package com.mcgrady.xproject.ui.gold.fragment;

import com.mcgrady.xproject.R;
import com.mcgrady.xproject.app.Constants;
import com.mcgrady.xproject.base.RootFragment;
import com.mcgrady.xproject.model.bean.GoldListBean;
import com.mcgrady.xproject.model.entity.GoldListEntity;
import com.mcgrady.xproject.presenter.gold.GoldContract;
import com.mcgrady.xproject.presenter.gold.GoldPagerPresenter;
import com.mcgrady.xproject.ui.gold.adapter.GoldListAdapter;
import com.mcgrady.xproject.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcgrady on 2017/10/23.
 *
 */

public class GoldPagerFragment extends RootFragment<GoldPagerPresenter> implements GoldContract.View {

    private GoldListAdapter mAdapter;
    private String mType;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gold_page;
    }

    @Override
    protected void init() {
        super.init();
//        mType = getArguments().getString(Constants.IT_GOLD_TYPE);
        mAdapter = new GoldListAdapter(new ArrayList<GoldListEntity>());

        mPresenter.getGoldData(Constants.IT_GOLD_TYPE);
    }

    @Override
    public void showContent(List<GoldListBean> goldListBean) {
        LogUtil.d(goldListBean.toString());
    }

    @Override
    public void showMoreContent(List<GoldListBean> goldMoreListBean, int start, int end) {

    }
}
