package com.mcgrady.xskeleton.base;

import android.os.Bundle;
import android.view.InflateException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mcgrady.xskeleton.cache.Cache;
import com.mcgrady.xskeleton.cache.CacheType;
import com.mcgrady.xskeleton.lifecycle.ActivityLifecycleable;
import com.mcgrady.xskeleton.utils.XUtils;
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
            cache = XUtils.obtainAppComponentFromContext().cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return cache;
    }
}
