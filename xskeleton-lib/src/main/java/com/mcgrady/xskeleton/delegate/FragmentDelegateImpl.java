package com.mcgrady.xskeleton.delegate;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.LogUtils;
import com.mcgrady.xskeleton.base.IFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by mcgrady on 2020/4/23.
 */
public class FragmentDelegateImpl implements FragmentDelegate {

    private FragmentManager fragmentManager;
    private Fragment fragment;
    private IFragment iFragment;
    private Unbinder unbinder;

    public FragmentDelegateImpl(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        this.fragmentManager = fragmentManager;
        this.fragment = fragment;
        this.iFragment = (IFragment) fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (iFragment.useEventBus()) {  //如果要使用eventbus请将此方法返回true
//            EventBusManager.getInstance().register(fragmentManager);//注册到事件主线
        }
    }

    @Override
    public void onCreateView(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //绑定到butterknife
        if (view != null) {
            unbinder = ButterKnife.bind(fragment, view);
        }
    }

    @Override
    public void onActivityCreate(@Nullable Bundle savedInstanceState) {
        iFragment.initData(savedInstanceState);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void onDestroyView() {
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            try {
                unbinder.unbind();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                //fix Bindings already cleared
                LogUtils.w("onDestroyView: %s", e.getMessage());
            }
        }
    }

    @Override
    public void onDestroy() {

        if (iFragment != null && iFragment.useEventBus()) { //如果要使用eventbus请将此方法返回true
//            EventBusManager.getInstance().unregister(fragment);//注册到事件主线
        }

        this.unbinder = null;
        this.fragmentManager = null;
        this.fragment = null;
        this.iFragment = null;
    }

    @Override
    public void onDetach() {

    }

    /**
     * Return true if the fragment is currently added to its activity.
     */
    @Override
    public boolean isAdded() {
        return fragment != null && fragment.isAdded();
    }
}
