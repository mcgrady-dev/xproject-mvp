package com.mcgrady.common_social;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mcgrady.xskeleton.base.BaseFragment;
import com.mcgrady.xskeleton.base.IPresenter;


/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/12/27
 */

public class ShareScreenShotFragment extends BaseFragment {

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_share_screenshot;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }
}
