package com.mcgrady.xskeleton.mvp;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by mcgrady on 2019/4/25.
 */
public class BasePresenter<M extends IModel, V extends IView> implements IPresneter {
    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;
    protected M mModel;
    protected V mView;

    public BasePresenter(@NonNull M model, @NonNull V view) {
        this.mModel = model;
        this.mView = view;
        onStart();
    }

    public BasePresenter(@NonNull V view) {
        this.mView = view;
        onStart();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        unDispose();
        if (mModel != null) {
            mModel.onDestory();
        }
        this.mModel = null;
        this.mView = null;
        this.mCompositeDisposable = null;
    }

    /**
     * 添加 {@link Disposable} 统一管理
     * @param disposable
     */
    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }

        mCompositeDisposable.add(disposable);
    }

    /**
     * 解除订阅
     * 停止集合中正在进行的 RxJava 任务，保证 Activity 结束时取消所有正在执行的订阅
     */
    public void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
