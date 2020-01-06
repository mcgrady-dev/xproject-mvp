package com.mcgrady.news.mvp.presenter;

import android.text.TextUtils;

import com.hjq.toast.ToastUtils;
import com.mcgrady.news.mvp.contract.ZhihuDailyHomeContract;
import com.mcgrady.news.mvp.model.entity.ZhihuDailyStoriesBean;
import com.mcgrady.xskeleton.base.BasePresenter;
import com.mcgrady.xskeleton.http.handler.ErrorHandleSubscriber;
import com.mcgrady.xskeleton.http.handler.RetryWithDelay;
import com.mcgrady.xskeleton.http.handler.RxErrorHandler;
import com.mcgrady.xskeleton.utils.RxLifecycleUtils;

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
public class ZhihuDailyHomePresenter extends BasePresenter<ZhihuDailyHomeContract.Model, ZhihuDailyHomeContract.View> {

    RxErrorHandler mErrorHandler;
    String mDate;

    public ZhihuDailyHomePresenter(ZhihuDailyHomeContract.Model model, ZhihuDailyHomeContract.View rootView) {
        super(model, rootView);
    }

    public void requestDailyList() {
        mModel.getDailyList()
            .subscribeOn(Schedulers.io())
            .retryWhen(new RetryWithDelay(3, 2))
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally(() -> mView.finishRefresh())
            .compose(RxLifecycleUtils.bindToLifecycle(mView))
            .subscribe(new ErrorHandleSubscriber<ZhihuDailyStoriesBean>(mErrorHandler) {
                @Override
                public void onNext(ZhihuDailyStoriesBean zhihuDailyStoriesBean) {
                    mDate = zhihuDailyStoriesBean.getDate();
                    mView.notifyDataSetChanged(zhihuDailyStoriesBean);
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
            .doFinally(() -> mView.finishLoadMore(true))
            .compose(RxLifecycleUtils.bindToLifecycle(mView))
            .subscribe(new ErrorHandleSubscriber<ZhihuDailyStoriesBean>(mErrorHandler) {
                @Override
                public void onNext(ZhihuDailyStoriesBean zhihuDailyStoriesBean) {
                    mDate = zhihuDailyStoriesBean.getDate();
                    mView.loadMoreData(zhihuDailyStoriesBean);
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    mView.finishLoadMore(true);
                }
            });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
}
