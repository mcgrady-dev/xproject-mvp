package com.mcgrady.xskeleton.integration;

/**
 * Created by mcgrady on 2019/4/25.
 */
public interface IAppConfigModule {

    void applyOptions();

    void injectAppLifecycle();

    void injectActivityLifecycle();

    void injectFragmentLifecycle();
}
