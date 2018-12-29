package com.mcgrady.core.base;

import com.mcgrady.core.di.component.AppComponent;
import com.mcgrady.core.di.component.DaggerAppComponent;
import com.mcgrady.core.http.IHttpHelper;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/9/19
 */

public interface ISupportApplication {

    AppComponent getAppComponent();

    DaggerAppComponent.Builder getAppComponentBuilder();

    IHttpHelper.NetConfig getNetConfig();
}
