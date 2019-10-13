package com.mcgrady.xskeleton.base;

import android.os.Bundle;
import android.view.InflateException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.mcgrady.xskeleton.lifecycle.ActivityLifecycleable;
import com.mcgrady.xskeleton.widget.LoadingDialog;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by mcgrady on 2019-08-10.
 */
public abstract class BaseActivity<P extends IPresenter> extends RxAppCompatActivity implements IActivity,
        ActivityLifecycleable {

    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    private Unbinder mUnbinder;
    private LoadingDialog loadingDialog;

    @Nullable
    protected P mPresenter;

    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        super.onCreate(savedInstanceState);
        try {
            int layoutResId = getLayoutResId();
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResId != 0) {
                setContentView(layoutResId);
                mUnbinder = ButterKnife.bind(this);
                initImmersionBar();
                initView(savedInstanceState);
            }
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        initData(savedInstanceState);
    }

    @NonNull
    @Override
    public Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .keyboardEnable(true)
                .transparentBar()
                .statusBarDarkFont(true)
                .fullScreen(false)
                .init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        mUnbinder = null;
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
        }
        this.mPresenter = null;

        //如果要使用 Eventbus 请将此方法返回 true
        if (useEventBus()) {
            //解除注册 Eventbus
            //EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void showProgress() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }

        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public boolean useEventBus() {
        return false;
    }
}
