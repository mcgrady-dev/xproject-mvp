package com.mcgrady.core.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * <p>Activity栈表</p>
 * @author: mcgrady
 * @date: 2018/5/9
 */
public class ActivityStack {

    private static Stack<Activity> activityStack = new Stack<>();
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
            activityStack.remove(activity);
        }
    }

    /**
     * 弹出当前activity并销毁
     */
    public void popActivity() {
        if(activityStack.size() != 0) {
            Activity ac = activityStack.pop();
            ac.finish();
            activityStack.remove(ac);
        }
    }

    /**
     * 将指定Activity推入栈中
     * @param activity
     */
    public void pushActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 退出栈中所有Activity
     */
    public void clearAllActivity() {
        while (!activityStack.isEmpty()) {
            Activity activity = activityStack.pop();
            popActivity(activity);
        }
    }
}
