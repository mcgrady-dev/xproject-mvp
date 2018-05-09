package com.mcgrady.core.utils;

import android.content.Context;

/**
 * <p>保存application cotext实例</p>
 * @author: mcgrady
 * @date: 2018/5/9
 */
public class AppContext {
  public static Context sContext;


  public static void init(Context context) {
    if (sContext == null) {
        sContext = context.getApplicationContext();
    } else {
      throw new IllegalStateException("set context duplicate");
    }
  }

  public static Context get() {
    if (sContext == null) {
      throw new IllegalStateException("forget init?");
    } else {
      return sContext;
    }
  }
}
