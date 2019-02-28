package com.mcgrady.news.mvp.presenter;

import android.text.TextUtils;

import com.hjq.toast.ToastUtils;
import com.mcgrady.common_core.di.scope.ActivityScope;
import com.mcgrady.common_core.handler.ErrorHandleSubscriber;
import com.mcgrady.common_core.handler.RetryWithDelay;
import com.mcgrady.common_core.handler.RxErrorHandler;
import com.mcgrady.common_core.mvp.BasePresenter;
import com.mcgrady.common_core.utils.RxLifecycleUtils;
import com.mcgrady.news.mvp.contract.ZhihuDailyHomeContract;
import com.mcgrady.news.mvp.model.entity.ZhihuDailyStoriesBean;

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
public class ZhihuDailyHomePresenter extends BasePresenter<ZhihuDailyHomeContract.Model, ZhihuDailyHomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    String mDate;

    @Inject
    public ZhihuDailyHomePresenter(ZhihuDailyHomeContract.Model model, ZhihuDailyHomeContract.View rootView) {
        super(model, rootView);
    }

    public void requestDailyList() {
        mModel.getDailyList()
            .subscribeOn(Schedulers.io())
            .retryWhen(new RetryWithDelay(3, 2))
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally(() -> mRootView.finishRefresh())
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(new ErrorHandleSubscriber<ZhihuDailyStoriesBean>(mErrorHandler) {
                @Override
                public void onNext(ZhihuDailyStoriesBean zhihuDailyStoriesBean) {
                    mDate = zhihuDailyStoriesBean.getDate();
                    mRootView.notifyDataSetChanged(zhihuDailyStoriesBean);
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
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
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally(() -> mRootView.finishLoadMore())
            .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
            .subscribe(new ErrorHandleSubscriber<ZhihuDailyStoriesBean>(mErrorHandler) {
                @Override
                public void onNext(ZhihuDailyStoriesBean zhihuDailyStoriesBean) {
                    mDate = zhihuDailyStoriesBean.getDate();
                    mRootView.loadMoreData(zhihuDailyStoriesBean);
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    mRootView.finishLoadMore(false);
                }
            });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
