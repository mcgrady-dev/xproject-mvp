package com.mcgrady.main.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.hjq.toast.ToastUtils;
import com.mcgrady.common_core.RouterHub;
import com.mcgrady.main.mvp.contract.CommonLoginContract;
import com.mcgrady.xskeleton.base.BasePresenter;
import com.mcgrady.xskeleton.http.handler.RxErrorHandler;


/**
 * 公共的登录逻辑
 */
public class CommonLoginPresenter extends BasePresenter<CommonLoginContract.Model, CommonLoginContract.View> {
    RxErrorHandler mErrorHandler;
    Application mApplication;
//    ImageLoader mImageLoader;

    public CommonLoginPresenter(CommonLoginContract.Model model, CommonLoginContract.View rootView) {
        super(model, rootView);
    }

    public void forgetPassword() {

    }

    public void loginByAccount(String user_name, String password) {

    }

    public void loginByMobile(String mobile_num, String ver_code) {
        if (!TextUtils.isEmpty(mobile_num) && !TextUtils.isEmpty(ver_code)) {
            ToastUtils.show("登录成功");
            mView.navigation(RouterHub.ZHIHU_DAILY_HOME);
            mView.finish();
        } else {
            ToastUtils.show("登录失败");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
//        this.mImageLoader = null;
        this.mApplication = null;
    }
}
