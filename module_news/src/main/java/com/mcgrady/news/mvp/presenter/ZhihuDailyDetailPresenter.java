package com.mcgrady.news.mvp.presenter;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.mcgrady.news.mvp.contract.ZhihuDailyDetailContract;
import com.mcgrady.news.mvp.model.entity.ZhihuDailyDetailBean;
import com.mcgrady.xskeleton.base.BasePresenter;
import com.mcgrady.xskeleton.http.handler.ErrorHandleSubscriber;
import com.mcgrady.xskeleton.http.handler.RetryWithDelay;
import com.mcgrady.xskeleton.http.handler.RxErrorHandler;
import com.mcgrady.xskeleton.utils.RxLifecycleUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * <p>知乎日报详情Presenter</p>
 *
 * @author: mcgrady
 * @date: 2019/2/28
 */

public class ZhihuDailyDetailPresenter extends BasePresenter<ZhihuDailyDetailContract.Model, ZhihuDailyDetailContract.View> {

    RxErrorHandler mErrorHandler;

    public ZhihuDailyDetailPresenter(ZhihuDailyDetailContract.Model model, ZhihuDailyDetailContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 使用 2017 Google IO 发布的 Architecture Components 中的 Lifecycles 的新特性 (此特性已被加入 Support library)
     * 使 {@code Presenter} 可以与 {@link Activity} 和 {@link Fragment} 的部分生命周期绑定
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
    }

    public void requestDailyDetail(int dailyId) {
        mModel.getDailyDetail(dailyId)
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mView.get().hideProgress())
                .compose(RxLifecycleUtils.bindToLifecycle(mView.get()))
                .subscribe(new ErrorHandleSubscriber<ZhihuDailyDetailBean>(mErrorHandler) {
                    @Override
                    public void onNext(ZhihuDailyDetailBean bean) {
                        mView.get().setDailyDetail(bean);
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
