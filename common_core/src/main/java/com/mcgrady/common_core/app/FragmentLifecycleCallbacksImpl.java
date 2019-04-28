package com.mcgrady.common_core.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/16
 */

public class FragmentLifecycleCallbacksImpl extends FragmentManager.FragmentLifecycleCallbacks {

    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        LogUtils.i(f.toString() + " - onFragmentAttached");
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        LogUtils.i(f.toString() + " - onFragmentCreated");
        /**
         * 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建时重复利用已经创建的 Fragment。
         * https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
         * 如果在 XML 中使用 <Fragment/> 标签,的方式创建 Fragment 请务必在标签中加上 android:id 或者 android:tag 属性,否则 setRetainInstance(true) 无效
         * 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
         */
        f.setRetainInstance(true);
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        LogUtils.i(f.toString() + " - onFragmentViewCreated");
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        LogUtils.i(f.toString() + " - onFragmentActivityCreated");
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        LogUtils.i(f.toString() + " - onFragmentStarted");
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        LogUtils.i(f.toString() + " - onFragmentResumed");
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        LogUtils.i(f.toString() + " - onFragmentPaused");
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        LogUtils.i(f.toString() + " - onFragmentStopped");
    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        LogUtils.i(f.toString() + " - onFragmentSaveInstanceState");
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
        LogUtils.i(f.toString() + " - onFragmentViewDestroyed");
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        LogUtils.i(f.toString() + " - onFragmentDestroyed");
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
        LogUtils.i(f.toString() + " - onFragmentDetached");
    }
}
