package com.mcgrady.xskeleton.http.handler

import com.blankj.utilcode.util.LogUtils
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

/**
 * Created by mcgrady on 2019/4/26.
 */
class RetryWithDelay(private val maxRetries: Int, private val retryDelaySecond: Int) : Function<Observable<Throwable?>, ObservableSource<*>> {
    val TAG = this.javaClass.simpleName
    private var retryCount = 0
    @Throws(Exception::class)
    override fun apply(throwableObservable: Observable<Throwable?>): ObservableSource<*> {
        return throwableObservable
                .flatMap(Function<Throwable, ObservableSource<*>> { throwable ->
                    if (++retryCount <= maxRetries) { // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                        LogUtils.d(TAG, "Observable get error, it will try after " + retryDelaySecond
                                + " second, retry count " + retryCount)
                        return@Function Observable.timer(retryDelaySecond.toLong(),
                                TimeUnit.SECONDS)
                    }
                    // Max retries hit. Just pass the error along.
                    Observable.error<Any>(throwable)
                })
    }

}