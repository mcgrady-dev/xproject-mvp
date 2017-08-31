package com.mcgrady.xproject.ui.main.activity;

import com.mcgrady.xproject.R;
import com.mcgrady.xproject.base.BaseActivity;
import com.mcgrady.xproject.contract.MainContract;
import com.mcgrady.xproject.presenter.MainPresenter;
import com.mcgrady.xproject.ui.gold.fragment.GoldHomeFragment;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {



    GoldHomeFragment mGoldHomeFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }


    @Override
    protected void init() {
        super.init();

        mGoldHomeFragment = new GoldHomeFragment();
        loadRootFragment(R.id.fl_container, mGoldHomeFragment);
//        showHideFragment(mGoldHomeFragment);
    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void showTestDialog(String msg) {

    }
}
