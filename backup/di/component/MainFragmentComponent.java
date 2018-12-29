package com.mcgrady.main.di.component;

import com.mcgrady.core.di.FragmentScope;
import com.mcgrady.core.di.component.AppComponent;
import com.mcgrady.core.di.module.FragmentModule;
import com.mcgrady.main.ui.fragment.SplashFragment;

import dagger.Component;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/9/19
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface MainFragmentComponent {

    void inject(SplashFragment splashFragment);
}
