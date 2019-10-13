package com.mcgrady.xskeleton.lifecycle;

import com.trello.rxlifecycle3.android.FragmentEvent;

/**
 * 让 {@link Fragment} 实现此接口,即可正常使用 {@link RxLifecycle}
 *
 * Created by mcgrady on 2019/4/26.
 */
public interface FragmentLifecycleable extends Lifecycleable<FragmentEvent> {
}
