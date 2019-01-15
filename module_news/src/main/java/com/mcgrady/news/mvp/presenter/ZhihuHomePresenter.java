package com.mcgrady.news.mvp.presenter;

import android.app.Application;

import com.mcgrady.common_core.di.scope.ActivityScope;
import com.mcgrady.common_core.http.imageloader.ImageLoader;
import com.mcgrady.common_core.intergration.handler.ErrorHandleSubscriber;
import com.mcgrady.common_core.intergration.handler.RetryWithDelay;
import com.mcgrady.common_core.intergration.handler.RxErrorHandler;
import com.mcgrady.common_core.intergration.manager.AppManager;
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
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

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
                        mRootView.notifyDataSetChanged(dailyListBean.getStories());
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
