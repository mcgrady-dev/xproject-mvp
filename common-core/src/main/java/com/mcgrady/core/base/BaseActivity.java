package com.mcgrady.core.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.mcgrady.core.R;
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
public abstract class BaseActivity extends SupportActivity implements LifecycleProvider<ActivityEvent>, IDaggerInject {

    private static final String TAG = BaseActivity.class.getSimpleName();

    protected Context mContext;
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();
    private Unbinder mUnbinder;

    /**
     * 上次点击时间
     */
    private long lastClick = 0;

    /**
     * 封装findViewById方法
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initInject(savedInstanceState);
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        int layoutId = getLayoutId();
        if (layoutId > 0) {
            setContentView(layoutId);
            mUnbinder = ButterKnife.bind(this);
        }

        mContext = this;
        LogUtils.i(TAG, "activity: " + getClass().getSimpleName() + " onCreate()");
        initEventAndData(savedInstanceState);
    }

    protected void setToolBar(Toolbar toolBar, String title) {
        toolBar.setTitle(title);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolBar.setNavigationOnClickListener(view -> onBackPressedSupport());
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
     * 重新加载页面
     * @param isAnima 是否需要动画
     */
    public final void reload(boolean isAnima) {
        if (isAnima) {
            getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
            recreate();
        } else {
            Intent intent = getIntent();
            overridePendingTransition(0, 0);
            finish();
            overridePendingTransition(R.anim.fade_in, 0);
            startActivity(intent);
        }
    }

    public final void reload() {
        reload(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (-1 != getOptionMenuId()) {
            getMenuInflater().inflate(getOptionMenuId(), menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public int getOptionMenuId() {
        return -1;
    }

    /**
     * 该方法回调时机为：Activity回退栈内Fragment的数量 <=1 时,默认{@link Activity#finish()}
     * 请尽量复写该方法,避免复写onBackPress(),以保证SupportFragment内的onBackPressedSupport()回退事件正常执行
     */
    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    protected boolean isCompatible(int apiLevel) {
        return Build.VERSION.SDK_INT >= apiLevel;
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
