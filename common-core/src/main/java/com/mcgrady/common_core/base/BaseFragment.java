package com.mcgrady.common_core.base;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.gyf.immersionbar.components.SimpleImmersionProxy;
import com.mcgrady.common_core.widget.LoadingDialog;
import com.mcgrady.xskeleton.base.IPresenter;

/**
 * 区别于{@link com.mcgrady.xskeleton.base.BaseFragment}的是这里添加了第三方库依赖
 *
 * Created by mcgrady on 2020/4/28.
 */
public abstract class BaseFragment<P extends IPresenter> extends com.mcgrady.xskeleton.base.BaseFragment<P> implements SimpleImmersionOwner
        ,LoadingDialogOwner {

    private LoadingDialog loadingDialog;
    private SimpleImmersionProxy simpleImmersionProxy = new SimpleImmersionProxy(this);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        simpleImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        simpleImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        simpleImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        simpleImmersionProxy.onConfigurationChanged(newConfig);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        simpleImmersionProxy.onDestroy();
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .keyboardEnable(true)
                .transparentBar()
                .statusBarDarkFont(true)
                .fullScreen(false)
                .init();
    }

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

    @Override
    public synchronized void showProgress() {
        if (mContext instanceof LoadingDialogOwner) {
            ((LoadingDialogOwner) mContext).showProgress();
        } else {
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog(mContext);
            }

            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }
    }

    @Override
    public synchronized void hideProgress() {
        if (mContext instanceof LoadingDialogOwner) {
            ((LoadingDialogOwner) mContext).hideProgress();
        } else {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
    }
}