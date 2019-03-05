package com.mcgrady.news.mvp.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;

import com.mcgrady.common_core.di.scope.ActivityScope;
import com.mcgrady.common_core.handler.ErrorHandleSubscriber;
import com.mcgrady.common_core.handler.RetryWithDelay;
import com.mcgrady.common_core.handler.RxErrorHandler;
import com.mcgrady.common_core.mvp.BasePresenter;
import com.mcgrady.common_core.utils.RxLifecycleUtils;
import com.mcgrady.news.mvp.contract.ZhihuDailyDetailContract;
import com.mcgrady.news.mvp.model.entity.ZhihuDailyDetailBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * <p>知乎日报详情Presenter</p>
 *
 * @author: mcgrady
 * @date: 2019/2/28
 */

@ActivityScope
public class ZhihuDailyDetailPresenter extends BasePresenter<ZhihuDailyDetailContract.Model, ZhihuDailyDetailContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public ZhihuDailyDetailPresenter(ZhihuDailyDetailContract.Model model, ZhihuDailyDetailContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 使用 2017 Google IO 发布的 Architecture Components 中的 Lifecycles 的新特性 (此特性已被加入 Support library)
     * 使 {@code Presenter} 可以与 {@link SupportActivity} 和 {@link Fragment} 的部分生命周期绑定
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
    }

    public void requestDailyDetail(int dailyId) {
        mModel.getDailyDetail(dailyId)
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<ZhihuDailyDetailBean>(mErrorHandler) {
                    @Override
                    public void onNext(ZhihuDailyDetailBean bean) {
                        mRootView.setDailyDetail(bean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler = null;
    }
}
