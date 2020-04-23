package com.mcgrady.xskeleton.base;

import android.os.Bundle;
import android.view.InflateException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.mcgrady.xskeleton.cache.Cache;
import com.mcgrady.xskeleton.cache.CacheType;
import com.mcgrady.xskeleton.lifecycle.ActivityLifecycleable;
import com.mcgrady.xskeleton.widget.LoadingDialog;
import com.trello.rxlifecycle3.android.ActivityEvent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by mcgrady on 2019-08-10.
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IActivity,
        ActivityLifecycleable {

    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();
    @Nullable
    protected P presenter;
    private Cache<String, Object> cache;
    private LoadingDialog loadingDialog;
    private Unbinder unbinder;

    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        super.onCreate(savedInstanceState);
        try {
            int layoutResId = getLayoutResId();
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResId != 0) {
                setContentView(layoutResId);
                unbinder = ButterKnife.bind(this);
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
        return lifecycleSubject;
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
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
        }
        unbinder = null;
        if (presenter != null) {
            presenter.onDestroy();//释放资源
        }
        this.presenter = null;

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

    @Override
    public boolean useFragment() {
        return false;
    }

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (cache == null) {
            //noinspection unchecked
            cache = AppComponent.obtainAppModule(this).getCacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return cache;
    }
}
