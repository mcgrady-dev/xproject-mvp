package com.mcgrady.news.mvp.presenter;

import android.text.TextUtils;

import com.hjq.toast.ToastUtils;
import com.mcgrady.common_core.di.scope.ActivityScope;
import com.mcgrady.common_core.intergration.handler.ErrorHandleSubscriber;
import com.mcgrady.common_core.intergration.handler.RetryWithDelay;
import com.mcgrady.common_core.intergration.handler.RxErrorHandler;
import com.mcgrady.common_core.mvp.BasePresenter;
import com.mcgrady.common_core.utils.RxLifecycleUtils;
import com.mcgrady.news.mvp.contract.ZhihuHomeContract;
import com.mcgrady.news.mvp.model.entity.DailyListBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/15/2019 13:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ZhihuHomePresenter extends BasePresenter<ZhihuHomeContract.Model, ZhihuHomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    String mDate;

    @Inject
    public ZhihuHomePresenter(ZhihuHomeContract.Model model, ZhihuHomeContract.View rootView) {
        super(model, rootView);
    }

    public void requestDailyList() {
        mModel.getDailyList()
            .subscribeOn(Schedulers.io())
            .retryWhen(new RetryWithDelay(3, 2))
            .doOnSubscribe(disposable -> mRootView.showLoading()).subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally(() -> mRootView.hideLoading()).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(new ErrorHandleSubscriber<DailyListBean>(mErrorHandler) {
                @Override
                public void onNext(DailyListBean dailyListBean) {
                    mDate = dailyListBean.getDate();
                    mRootView.setBanner(dailyListBean.getTop_stories());
                    mRootView.notifyDataSetChanged(dailyListBean.getStories());
                }
            });
    }

    public void requestBeforeDailyList() {
        if (TextUtils.isEmpty(mDate)) {
            ToastUtils.show("暂无数据");
            return;
        }
        mModel.getBeforeDailyList(mDate)
            .subscribeOn(Schedulers.io())
            .retryWhen(new RetryWithDelay(3, 2))
            .doOnSubscribe(disposable -> mRootView.showLoading())
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally(() -> mRootView.hideLoading())
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(new ErrorHandleSubscriber<DailyListBean>(mErrorHandler) {
                @Override
                public void onNext(DailyListBean dailyListBean) {
                    mDate = dailyListBean.getDate();
                    mRootView.loadMoreData(dailyListBean.getStories());
                }
            });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
