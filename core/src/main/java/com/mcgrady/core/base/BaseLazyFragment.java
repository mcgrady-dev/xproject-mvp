package com.mcgrady.core.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CheckResult;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.LogUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/24
 * @des: fragment基类，基于fragmentation封装
 */
public abstract class BaseLazyFragment extends SupportFragment implements LifecycleProvider<FragmentEvent>, IDaggerInject {
    private static final String TAG = BaseLazyFragment.class.getSimpleName();
    public static final Handler handler = new Handler();

    protected View mView;
    private boolean isInited = false;
    protected Context mContext;
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    public final Handler getHandler() {
        return handler;
    }

    protected <T extends View> T $(int resId) {
        return (T) mView.findViewById(resId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        int layoutId = getLayoutId();
        if (layoutId > 0) {
            mView = inflater.inflate(layoutId, null);
        }

        mView.setVisibility(View.VISIBLE);
        mView.getVisibility();

        initInject(savedInstanceState);
        return mView;
    }

    /**
     * 默认为横向切换动画
     * @return
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    /**
     * View创建完成的回调
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!enableLayzLoad()) {
            isInited = true;
            initEventAndData();
        }

        LogUtils.i(TAG, this.getClass().getSimpleName() + " onViewCreated()");
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (enableLayzLoad()) {
            isInited = true;
            LogUtils.i(TAG, "fragment: " + getClass().getSimpleName() + " onLazyInitView()");
        }
    }

    /**
     * 默认启用懒加载模式
     * @return
     */
    public boolean enableLayzLoad() {
        return true;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }

    /**
     * 当Fragment对用户可见时回调
     */
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }


    /**
     * 当Fragment对用户状态为Invisible时回调
     */
    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initEventAndData();


    ///////////////////////////////////////////////////////////////////////////
    // keyboard start
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 延时弹出键盘，默认延时300毫秒
     * @param focus
     */
    protected void showKeyboardDelayed(View focus) {
        showKeyboardDelayed(focus, 300);
    }

    protected void showKeyboardDelayed(View focus, long delayMillis) {
        final View viewToFocus = focus;
        if (focus != null) {
            focus.requestFocus();
        }

        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewToFocus == null || viewToFocus.isFocused()) {
                    showKeyboard(true);
                }
            }
        }, delayMillis);
    }

    protected void showKeyboard(boolean isShow) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        if (isShow) {
            if (activity.getCurrentFocus() == null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } else {
                imm.showSoftInput(activity.getCurrentFocus(), 0);
            }
        } else {
            if (activity.getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }
    }

    /**
     * 软键盘是否弹出
     * @return
     */
    public boolean isKeyboardVisible() {
        if (_mActivity != null && getView() != null) {
            InputMethodManager imm = (InputMethodManager) _mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.hideSoftInputFromWindow(getView().getWindowToken(), 0)) {
                imm.showSoftInput(getView(), 0);
                return true;
            }
        }

        return false;
    }

    ///////////////////////////////////////////////////////////////////////////
    // keyboard end
    ///////////////////////////////////////////////////////////////////////////



    ///////////////////////////////////////////////////////////////////////////
    // RxLifecycle start
    ///////////////////////////////////////////////////////////////////////////

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }


    @Override
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
        mView = null;
        mContext = null;
        LogUtils.i(TAG, "fragment: " + getClass().getSimpleName() + " onDestroyView()");

    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }


    ///////////////////////////////////////////////////////////////////////////
    // RxLifecycle end
    ///////////////////////////////////////////////////////////////////////////
}
