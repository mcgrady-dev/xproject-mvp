package com.mcgrady.xskeleton.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mcgrady.xskeleton.cache.Cache;
import com.mcgrady.xskeleton.cache.CacheType;
import com.mcgrady.xskeleton.lifecycle.FragmentLifecycleable;
import com.mcgrady.xskeleton.widget.LoadingDialog;
import com.trello.rxlifecycle3.android.FragmentEvent;
import com.trello.rxlifecycle3.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by mcgrady on 2019/4/26.
 */
public abstract class BaseFragment<P extends IPresenter> extends RxFragment implements IFragment, FragmentLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    protected Context mContext;
    private View rootView;
    private LoadingDialog loadingDialog;
    private Unbinder mUnbinder;

    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null
    protected abstract P createPresenter();

    private Cache<String, Object> cache;

    @NonNull
    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResId(), container, false);
            mUnbinder = ButterKnife.bind(this, rootView);
            initView(savedInstanceState);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fitsLayoutOverlap();
    }

    protected void fitsLayoutOverlap() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
        this.mContext = null;
    }

    @Override
    public void showProgress() {
        if (mContext instanceof IActivity) {
            ((IActivity) mContext).showProgress();
        } else {
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog(mContext);
            }

            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }
    }

    @Override
    public void hideProgress() {
        if (mContext instanceof IActivity) {
            ((IActivity) mContext).hideProgress();
        } else {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (cache == null) {
            //noinspection unchecked
            cache = AppComponent.obtainAppModule(getActivity()).getCacheFactory().build(CacheType.FRAGMENT_CACHE);
        }

        return cache;
    }
}
