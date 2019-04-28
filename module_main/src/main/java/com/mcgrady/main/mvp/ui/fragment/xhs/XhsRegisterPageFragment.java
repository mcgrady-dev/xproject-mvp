package com.mcgrady.main.mvp.ui.fragment.xhs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcgrady.main.R;
import com.mcgrady.xskeleton.base.BaseFragment;
import com.mcgrady.xskeleton.di.component.AppComponent;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/12/27
 */

public class XhsRegisterPageFragment extends BaseFragment {
    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment_xhs_register, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }
}
