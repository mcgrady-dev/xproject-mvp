package com.mcgrady.xskeleton.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by mcgrady on 2019-08-10.
 */
public class BasePresenter<M extends IModel, V extends IView> implements IPresenter {
    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;
    protected M mModel;
    protected WeakReference<V> mView;

    public BasePresenter(@Nullable V view) {
        this(null, view);
    }

    public BasePresenter(@NonNull M mModel, @NonNull V mView) {
        this.mModel = mModel;
        this.mView = new WeakReference<>(mView);
    }

    @Override
    public void onStart() {

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

    @Override
    public void onDestroy() {
        unDispose();
        if (mModel != null) {
            mModel.onDestroy();
        }
    }
}
