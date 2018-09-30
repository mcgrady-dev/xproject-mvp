package com.mcgrady.main.di;

import com.mcgrady.core.base.BaseApplication;
import com.mcgrady.core.di.module.FragmentModule;
import com.mcgrady.core.utils.AppContext;
import com.mcgrady.main.di.component.DaggerMainFragmentComponent;
import com.mcgrady.main.di.component.MainFragmentComponent;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/9/19
 */

public class MainDiHelper {

    public static MainFragmentComponent getFragmentComponent(FragmentModule fragmentModule) {
        return DaggerMainFragmentComponent.builder()
                .appComponent(((BaseApplication) AppContext.get()).getAppComponent())
                .fragmentModule(fragmentModule)
                .build();
    }

}
