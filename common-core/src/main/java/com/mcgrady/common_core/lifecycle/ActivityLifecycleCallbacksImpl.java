package com.mcgrady.common_core.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mcgrady.common_core.utils.ViewUtils;

import timber.log.Timber;

/**
 *
 * @author: mcgrady
 * @date: 2019/1/16
 */

public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Timber.i(activity + " - onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Timber.i(activity + " - onActivityStarted");
        if (!activity.getIntent().getBooleanExtra("isInitToolbar", false)) {
            //由于加强框架的兼容性,故将 setContentView 放到 onActivityCreated 之后,onActivityStarted 之前执行
            //而 findViewById 必须在 Activity setContentView() 后才有效,所以将以下代码从之前的 onActivityCreated 中移动到 onActivityStarted 中执行
            activity.getIntent().putExtra("isInitToolbar", true);
            //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里
            if (ViewUtils.findViewByName(activity.getApplicationContext(), activity, "public_toolbar") != null) {
                if (activity instanceof AppCompatActivity) {
                    ((AppCompatActivity) activity).setSupportActionBar(ViewUtils.findViewByName(activity.getApplicationContext(), activity, "public_toolbar"));
                    ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
                } else {
                    //FIXME
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        activity.setActionBar(ViewUtils.findViewByName(activity.getApplicationContext(), activity, "public_toolbar"));
//                        activity.getActionBar().setDisplayShowTitleEnabled(false);
//                    }
                }
            }
            if (ViewUtils.findViewByName(activity.getApplicationContext(), activity, "public_toolbar_title") != null) {
                ((TextView) ViewUtils.findViewByName(activity.getApplicationContext(), activity, "public_toolbar_title")).setText(activity.getTitle());
            }
            if (ViewUtils.findViewByName(activity.getApplicationContext(), activity, "public_toolbar_back") != null) {
                ViewUtils.findViewByName(activity.getApplicationContext(), activity, "public_toolbar_back").setOnClickListener(v -> {
                    activity.onBackPressed();
                });
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Timber.i(activity + " - onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Timber.i(activity + " - onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Timber.i(activity + " - onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Timber.i(activity + " - onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Timber.i(activity + " - onActivityDestroyed");
        //横竖屏切换或配置改变时, Activity 会被重新创建实例, 但 Bundle 中的基础数据会被保存下来,移除该数据是为了保证重新创建的实例可以正常工作
        activity.getIntent().removeExtra("isInitToolbar");
    }
}
