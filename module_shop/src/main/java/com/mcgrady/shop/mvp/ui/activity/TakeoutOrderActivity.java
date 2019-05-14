package com.mcgrady.shop.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.mcgrady.shop.R;
import com.mcgrady.shop.R2;
import com.mcgrady.xskeleton.base.BaseActivity;
import com.mcgrady.xskeleton.di.component.AppComponent;

import butterknife.BindView;

/**
 * Created by mcgrady on 2019/5/13.
 */
public class TakeoutOrderActivity extends BaseActivity {

    @BindView(R2.id.shop_recycler_left_menu)
    RecyclerView recyclerLeftMenu;
    @BindView(R2.id.shop_recycler_right_menu)
    RecyclerView recyclerRightMenu;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.shop_activity_takeout_order;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
