package com.mcgrady.main.mvp.presenter;

import android.app.Application;

import com.mcgrady.main.mvp.contract.CommonRegisterContract;
import com.mcgrady.xskeleton.di.scope.ActivityScope;
import com.mcgrady.xskeleton.http.handler.RxErrorHandler;
import com.mcgrady.xskeleton.imageloader.ImageLoader;
import com.mcgrady.xskeleton.mvp.BasePresenter;

import javax.inject.Inject;

@ActivityScope
public class CommonRegisterPresenter extends BasePresenter<CommonRegisterContract.Model, CommonRegisterContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;

    @Inject
    public CommonRegisterPresenter(CommonRegisterContract.Model model, CommonRegisterContract.View rootView) {
        super(model, rootView);
    }

    public void registerByPassword() {

    }

    public void registerByMobile() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
