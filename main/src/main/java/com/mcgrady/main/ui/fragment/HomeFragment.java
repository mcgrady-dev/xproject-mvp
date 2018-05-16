package com.mcgrady.main.ui.fragment;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mcgrady.core.base.BaseFragment;
import com.mcgrady.main.R;

/**
 * <p>启动页</p>
 *
 * @author: mcgrady
 * @date: 2018/5/16
 */
@Route(path = "/main/HomeFragment")
public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_home;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void initInject(Bundle savedInstanceState) {

    }
}
