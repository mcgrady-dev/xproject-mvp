package com.mcgrady.xproject.zhihu.mvp.presenter;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.mcgrady.xproject.zhihu.mvp.contract.ZhihuDailyHomeContract;
import com.mcgrady.xproject.zhihu.mvp.model.entity.ZhihuDailyStoriesBean;
import com.mcgrady.xskeleton.base.BasePresenter;
import com.mcgrady.xskeleton.http.handler.ErrorHandleSubscriber;
import com.mcgrady.xskeleton.http.handler.RetryWithDelay;
import com.mcgrady.xskeleton.http.handler.RxErrorHandler;
import com.mcgrady.xskeleton.utils.RxLifecycleUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ZhihuDailyHomePresenter extends BasePresenter<ZhihuDailyHomeContract.Model, ZhihuDailyHomeContract.View> {

    RxErrorHandler errorHandler;
    String mDate;

    public ZhihuDailyHomePresenter(@NonNull ZhihuDailyHomeContract.Model mModel, @NonNull ZhihuDailyHomeContract.View mView, RxErrorHandler errorHandler) {
        super(mModel, mView);
        this.errorHandler = errorHandler;
    }

    public void requestDailyList() {
        mModel.getDailyList()
            .subscribeOn(Schedulers.io())
            .retryWhen(new RetryWithDelay(2, 2))
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally(() -> mViewRef.get().finishRefresh())
            .compose(RxLifecycleUtils.bindToLifecycle(mViewRef.get()))
            .subscribe(new ErrorHandleSubscriber<ZhihuDailyStoriesBean>(errorHandler) {
                @Override
                public void onNext(ZhihuDailyStoriesBean zhihuDailyStoriesBean) {
                    mDate = zhihuDailyStoriesBean.getDate();
                    mViewRef.get().notifyDataSetChanged(zhihuDailyStoriesBean);
                }
            });

//        //基于{@link BaseResponse}壳的用法
//        mModel.getDailList2()
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(disposable -> {
//                    mView.showProgress();
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doFinally(() -> mView.hideProgress())
//                .compose(RxLifecycleUtils.bindToLifecycle(mView))
//                .subscribe(new ErrorHandleSubscriberImpl<BaseResponse<ZhihuDailyStoriesBean>>(errorHandler) {
//                    @Override
//                    public void onNext(BaseResponse<ZhihuDailyStoriesBean> data) {
//                        super.onNext(data);
//
//                        mDate = data.getData().getDate();
//                        mView.notifyDataSetChanged(data.getData());
//                    }
//                });
    }

    public void requestBeforeDailyList() {
        if (TextUtils.isEmpty(mDate)) {
            mViewRef.get().finishLoadMore(false);
            return;
        }

        mModel.getBeforeDailyList(mDate)
            .subscribeOn(Schedulers.io())
            .retryWhen(new RetryWithDelay(3, 2))
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(RxLifecycleUtils.bindToLifecycle(mViewRef.get()))
            .subscribe(new ErrorHandleSubscriber<ZhihuDailyStoriesBean>(errorHandler) {
                @Override
                public void onNext(ZhihuDailyStoriesBean zhihuDailyStoriesBean) {
                    mViewRef.get().finishLoadMore(true);
                    mDate = zhihuDailyStoriesBean.getDate();
                    mViewRef.get().loadMoreData(zhihuDailyStoriesBean);
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    mViewRef.get().finishLoadMore(false);
                }
            });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.errorHandler = null;
    }
}
