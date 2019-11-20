package com.mcgrady.xskeleton.integration

import com.mcgrady.xskeleton.http.handler.RxErrorHandler
import com.mcgrady.xskeleton.http.interceptor.NetworkInterceptor
import com.mcgrady.xskeleton.http.log.DefaultFormatPrinter
import com.mcgrady.xskeleton.http.log.RequestInterceptor
import com.mcgrady.xskeleton.utils.Preconditions
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.TimeUnit

/**
 * Created by mcgrady on 2019-08-10.
 */
class RepositoryManager private constructor() : IRepositoryManager {
    private var mRetrofit: Retrofit? = null
    private var mOkHttpClient: OkHttpClient? = null
    private var mRxErrorHandler: RxErrorHandler? = null
    /** 必须设置  */
    fun setOkHttpClient(okHttpClient: OkHttpClient): RepositoryManager {
        Preconditions.Companion.checkNotNull(okHttpClient, "OkHttpClient can't be empty")
        mOkHttpClient = okHttpClient
        return this
    }

    fun setRetrofit(builder: Retrofit.Builder): RepositoryManager {
        Preconditions.Companion.checkNotNull(mOkHttpClient, "OkHttpClient can't be empty")
        mRetrofit = builder
                .client(mOkHttpClient)
                .build()
        return this
    }

    fun setRxErrorHandler(rxErrorHandler: RxErrorHandler?): RepositoryManager {
        mRxErrorHandler = rxErrorHandler
        return this
    }

    override val rxErrorHandler: RxErrorHandler?
        get() {
            Preconditions.Companion.checkNotNull(mRxErrorHandler, "RxErrorHandler is null")
            return mRxErrorHandler
        }

    @Synchronized
    override fun <T> obtainRetrofitService(serviceClass: Class<T>): T {
        return createWrapperService(serviceClass)
    }

    /**
     * 根据 https://zhuanlan.zhihu.com/p/40097338 对 Retrofit 进行的优化
     *
     * @param serviceClass ApiService class
     * @param <T> ApiService class
     * @return ApiService
    </T> */
    private fun <T> createWrapperService(serviceClass: Class<T>): T {
        Preconditions.Companion.checkNotNull(serviceClass, "serviceClass == null")
        // 二次代理
        return Proxy.newProxyInstance(serviceClass.classLoader, arrayOf<Class<*>>(serviceClass), InvocationHandler { proxy, method, args ->
            // 此处在调用 serviceClass 中的方法时触发
            if (method.returnType == Observable::class.java) { // 如果方法返回值是 Observable 的话，则包一层再返回，
// 只包一层 defer 由外部去控制耗时方法以及网络请求所处线程，
// 如此对原项目的影响为 0，且更可控。
                return@InvocationHandler Observable.defer {
                    val service = getRetrofitService(serviceClass)
                    getRetrofitMethod(service, method)
                            .invoke(service, *args) as Observable<*>
                }
            } else if (method.returnType == Single::class.java) { // 如果方法返回值是 Single 的话，则包一层再返回。
                return@InvocationHandler Single.defer {
                    val service = getRetrofitService(serviceClass)
                    getRetrofitMethod(service, method)
                            .invoke(service, *args) as Single<*>
                }
            }
            // 返回值不是 Observable 或 Single 的话不处理。
            val service = getRetrofitService(serviceClass)
            getRetrofitMethod(service, method).invoke(service, *args)
        }) as T
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass ApiService class
     * @param <T> ApiService class
     * @return ApiService
    </T> */
    private fun <T> getRetrofitService(serviceClass: Class<T>): T {
        Preconditions.Companion.checkNotNull(mRetrofit, "Retrofit can not be empty")
        return mRetrofit!!.create(serviceClass)
    }

    @Throws(NoSuchMethodException::class)
    private fun <T> getRetrofitMethod(service: T, method: Method): Method {
        return service.javaClass.getMethod(method.name, *method.parameterTypes)
    }

    private val okHttpClient: OkHttpClient
        private get() = OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(NetworkInterceptor())
                .addInterceptor(RequestInterceptor(RequestInterceptor.Level.ALL, DefaultFormatPrinter()))
                .build()

    private val retrofit: Retrofit
        private get() {
            val builder = Retrofit.Builder()
            return builder.baseUrl("")
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

    companion object {
        const val DEFAULT_TIMEOUT: Long = 10
        @Volatile
        private var sInstance: RepositoryManager? = null

        val instance: RepositoryManager?
            get() {
                if (sInstance == null) {
                    synchronized(RepositoryManager::class.java) {
                        if (sInstance == null) {
                            sInstance = RepositoryManager()
                        }
                    }
                }
                return sInstance
            }
    }
}