package com.mcgrady.xproject.shop.mvp.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mcgrady.common_core.base.BaseActivity;
import com.mcgrady.shop.R2;
import com.mcgrady.xproject.shop.R;
import com.mcgrady.xproject.shop.mvp.contract.TakeoutOrderContract;
import com.mcgrady.xproject.shop.mvp.presenter.TakeoutOrderPresenter;

import butterknife.BindView;

/**
 * Created by mcgrady on 2019/5/13.
 */
public class TakeoutOrderActivity extends BaseActivity<TakeoutOrderPresenter> implements TakeoutOrderContract.View {

    @BindView(R2.id.shop_recycler_left_menu)
    RecyclerView recyclerLeftMenu;
    @BindView(R2.id.shop_recycler_right_menu)
    RecyclerView recyclerRightMenu;


    @Override
    public int getLayoutResId() {
        return R.layout.shop_activity_takeout_order;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        recyclerLeftMenu.setLayoutManager(new LinearLayoutManager(this));
        recyclerRightMenu.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected TakeoutOrderPresenter createPresenter() {
        return null;
    }
}
