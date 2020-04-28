package com.mcgrady.common_res.base;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.mcgrady.common_res.widget.LoadingDialog;
import com.mcgrady.xskeleton.base.IPresenter;

/**
 * 区别于{@link com.mcgrady.xskeleton.base.BaseActivity}的是在这里添加第三方库依赖
 *
 * Created by mcgrady on 2020/4/28.
 */
public abstract class BaseActivity<P extends IPresenter> extends com.mcgrady.xskeleton.base.BaseActivity<P> implements SimpleImmersionOwner, LoadingDialogOwner {

    private LoadingDialog loadingDialog;

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
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }

        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public synchronized void hideProgress() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}


