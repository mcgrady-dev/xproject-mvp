package com.mcgrady.xproject.main.mvp.presenter;

import android.app.Application;

import com.mcgrady.xproject.main.mvp.contract.CommonRegisterContract;
import com.mcgrady.xskeleton.base.BasePresenter;
import com.mcgrady.xskeleton.http.handler.RxErrorHandler;


public class CommonRegisterPresenter extends BasePresenter<CommonRegisterContract.Model, CommonRegisterContract.View> {
    RxErrorHandler mErrorHandler;
    Application mApplication;
//    ImageLoader mImageLoader;

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
//        this.mImageLoader = null;
        this.mApplication = null;
    }
}
