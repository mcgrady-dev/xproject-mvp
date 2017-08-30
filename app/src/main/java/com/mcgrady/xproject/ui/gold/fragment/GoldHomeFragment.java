package com.mcgrady.xproject.ui.gold.fragment;

import com.mcgrady.xproject.R;
import com.mcgrady.xproject.base.BaseFragment;
import com.mcgrady.xproject.contract.GoldHomeContract;
import com.mcgrady.xproject.presenter.GoldHomePresenter;

/**
 * Created by mcgrady on 2017/8/30.
 * 稀土掘金主页
 */

public class GoldHomeFragment extends BaseFragment<GoldHomePresenter> implements GoldHomeContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gold_home;
    }

    @Override
    protected void init() {

    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void stateError() {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateMain() {

    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }
}
