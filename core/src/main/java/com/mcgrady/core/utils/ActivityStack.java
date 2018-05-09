package com.mcgrady.core.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * <p>activity栈表</p>
 * @author: mcgrady
 * @date: 2018/5/9
 */
public class ActivityStack {

    private static Stack<Activity> mActivityStack = new Stack<>();
    private static ActivityStack instance = null;

    public static synchronized ActivityStack getInstance() {
        if (instance == null) {
            instance = new ActivityStack();
        }
        return instance;
    }

    /**
     * 弹出指定activity并销毁
     * @param activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            mActivityStack.remove(activity);
        }
    }

    /**
     * 弹出当前activity并销毁
     */
    public void popActivity() {
        if(mActivityStack.size() != 0) {
            Activity ac = mActivityStack.pop();
            ac.finish();
            mActivityStack.remove(ac);
        }
    }

    /**
     * 将指定Activity推入栈中
     * @param activity
     */
    public void pushActivity(Activity activity) {
        mActivityStack.add(activity);
    }

    /**
     * 退出栈中所有Activity
     */
    public void clearAllActivity() {
        while (!mActivityStack.isEmpty()) {
            Activity activity = mActivityStack.pop();
            popActivity(activity);
        }
    }
}
