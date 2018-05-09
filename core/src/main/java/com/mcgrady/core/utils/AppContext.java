package com.mcgrady.core.utils;

import android.content.Context;

/**
 * <p>保存application cotext实例</p>
 * @author: mcgrady
 * @date: 2018/5/9
 */
public class AppContext {
  public static Context mAppContext;


  public static void init(Context context) {
    if (mAppContext == null) {
      mAppContext = context.getApplicationContext();
    } else {
      throw new IllegalStateException("set context duplicate");
    }
  }

  public static Context get() {
    if (mAppContext == null) {
      throw new IllegalStateException("forget init?");
    } else {
      return mAppContext;
    }
  }
}
