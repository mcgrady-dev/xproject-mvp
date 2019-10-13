package com.mcgrady.xskeleton.lifecycle;

import android.app.Activity;

import com.trello.rxlifecycle3.android.ActivityEvent;

/**
 * 让 {@link Activity} 实现此接口,即可正常使用 {@link RxLifecycle}
 *
 * Created by mcgrady on 2019/4/26.
 */
public interface ActivityLifecycleable extends Lifecycleable<ActivityEvent> {
}
