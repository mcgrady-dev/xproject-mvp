package com.mcgrady.xproject.ui.gold.fragment;

import com.mcgrady.xproject.base.RootFragment;
import com.mcgrady.xproject.model.bean.GoldListBean;
import com.mcgrady.xproject.presenter.gold.GoldContract;
import com.mcgrady.xproject.presenter.gold.GoldPresenter;

import java.util.List;

/**
 * Created by mcgrady on 2017/10/23.
 */

public class GoldPagerFragment extends RootFragment<GoldPresenter> implements GoldContract.View {

    @Override
    public void showContent(List<GoldListBean> goldListBean) {

    }

    @Override
    public void showMoreContent(List<GoldListBean> goldMoreListBean, int start, int end) {

    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
