package com.mcgrady.xproject.widget;

import android.text.TextUtils;

import com.mcgrady.xproject.base.BaseView;
import com.mcgrady.xproject.util.LogUtil;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by mcgrady on 2017/10/23.
 */

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private BaseView rootView;
    private String errorMsg;
    private boolean isShowErrorState = true;

    public CommonSubscriber(BaseView rootView) {
        this.rootView = rootView;
    }

    public CommonSubscriber(BaseView rootView, String errorMsg) {
        this.rootView = rootView;
        this.errorMsg = errorMsg;
    }

    public CommonSubscriber(BaseView rootView, boolean isShowErrorState) {
        this.rootView = rootView;
        this.isShowErrorState = isShowErrorState;
    }

    public CommonSubscriber(BaseView rootView, String errorMsg, boolean isShowErrorState) {
        this.rootView = rootView;
        this.errorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }


    @Override
    public void onError(Throwable t) {
        if (rootView == null)
            return;

        if (!TextUtils.isEmpty(errorMsg)) {
            rootView.showErrorMsg(errorMsg);
        } else {
            rootView.showErrorMsg("未知错误");
            LogUtil.d(t.toString());
        }

        if (isShowErrorState)
            rootView.stateError();
    }

    @Override
    public void onComplete() {

    }
}
