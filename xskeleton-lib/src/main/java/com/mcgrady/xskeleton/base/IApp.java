package com.mcgrady.xskeleton.base;

import androidx.annotation.NonNull;

/**
 * 框架要求框架中的每个 {@link android.app.Application} 都需要实现此类, 以满足规范
 *
 * Created by mcgrady on 2020/4/3.
 */
public interface IApp {

    @NonNull
    AppComponent getAppComponent();
}
