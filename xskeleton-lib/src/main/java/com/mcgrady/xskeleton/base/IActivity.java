package com.mcgrady.xskeleton.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

/**
 * Created by mcgrady on 2019-08-10.
 */
public interface IActivity {

    @LayoutRes
    int getLayoutResId();

    void initView(@Nullable Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    void initData(@Nullable Bundle savedInstanceState);

    /**
     * 是否使用 EventBus
     *
     * @return
     */
    boolean useEventBus();

    void showProgress();

    void hideProgress();
}
