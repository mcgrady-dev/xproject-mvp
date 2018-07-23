package com.mcgrady.main.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.mcgrady.core.base.BaseFragment;
import com.mcgrady.main.R;

/**
 * <p>启动页</p>
 *
 * @author: mcgrady
 * @date: 2018/5/16
 */
public class BottomNavigationFragment extends BaseFragment {

    public static BottomNavigationFragment newInstance() {
        Bundle args = new Bundle();
        BottomNavigationFragment fragment = new BottomNavigationFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_bottom_navigation;
    }

    @Override
    protected void initEventAndData(View view) {

    }

    @Override
    public void initInject(Bundle savedInstanceState) {

    }
}
