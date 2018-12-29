package com.mcgrady.core.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * <p>activity碎片基类</p>
 * @author: mcgrady
 * @date: 2018/5/9
 */
public abstract class BaseActivity extends SupportActivity implements LifecycleProvider<ActivityEvent> {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    private Unbinder mUnbinder;

    /**
     * 上次点击时间
     */
    private long lastClick = 0;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        int layoutId = getLayoutId();
        if (layoutId > 0) {
            setContentView(layoutId);
            mUnbinder = ButterKnife.bind(this);
        }

        LogUtils.i(TAG, "activity: " + getClass().getSimpleName() + " onCreate()");
        initEventAndData(savedInstanceState);
    }

    /**
     * 封装{@link View#findViewById(int)}方法
     * @param id
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }

    @Override
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        LogUtils.i(TAG, "activity: " + getClass().getSimpleName() + " onDestroy()");
    }

    /**
     * 该方法回调时机为：Activity回退栈内Fragment的数量 <=1 时,默认{@link Activity#finish()}
     * 请尽量复写该方法,避免复写onBackPress(),以保证SupportFragment内的onBackPressedSupport()回退事件正常执行
     */
    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initEventAndData(Bundle savedInstanceState);

    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }

    ///////////////////////////////////////////////////////////////////////////
    // RxLifecycle start
    ///////////////////////////////////////////////////////////////////////////

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }


    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    ///////////////////////////////////////////////////////////////////////////
    // RxLifecycle end
    ///////////////////////////////////////////////////////////////////////////
}
