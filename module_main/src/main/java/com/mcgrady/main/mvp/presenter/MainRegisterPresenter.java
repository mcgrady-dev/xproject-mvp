package com.mcgrady.main.mvp.presenter;

import android.app.Application;

import com.mcgrady.main.mvp.contract.MainRegisterContract;
import com.mcgrady.xskeleton.di.scope.ActivityScope;
import com.mcgrady.xskeleton.http.handler.RxErrorHandler;
import com.mcgrady.xskeleton.imageloader.ImageLoader;
import com.mcgrady.xskeleton.mvp.BasePresenter;

import javax.inject.Inject;

@ActivityScope
public class MainRegisterPresenter extends BasePresenter<MainRegisterContract.Model, MainRegisterContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;

    @Inject
    public MainRegisterPresenter(MainRegisterContract.Model model, MainRegisterContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
