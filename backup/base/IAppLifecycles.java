package com.mcgrady.core.base;

import android.app.Application;
import android.content.Context;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/9/30
 */

public interface IAppLifecycles {

    void attachBaseContext(Context context);

    void onCreate(Application application);

    void onTerminate(Application application);
}
