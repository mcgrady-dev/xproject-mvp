package com.mcgrady.xskeleton.lifecycle

import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.android.FragmentEvent

/**
 * 让 [Fragment] 实现此接口,即可正常使用 [RxLifecycle]
 *
 * Created by mcgrady on 2019/4/26.
 */
interface FragmentLifecycleable : Lifecycleable<FragmentEvent?>