package com.mcgrady.xskeleton.integration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mcgrady.xskeleton.http.handler.RxErrorHandler;
import com.mcgrady.xskeleton.http.interceptor.NetworkInterceptor;
import com.mcgrady.xskeleton.http.log.DefaultFormatPrinter;
import com.mcgrady.xskeleton.http.log.RequestInterceptor;
import com.mcgrady.xskeleton.utils.Preconditions;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mcgrady on 2019-08-10.
 */
public class RepositoryManager implements IRepositoryManager {

    public static final long DEFAULT_TIMEOUT = 10;

    private volatile static RepositoryManager sInstance;

    public static RepositoryManager getInstance() {
        if (sInstance == null) {
            synchronized (RepositoryManager.class) {
                if (sInstance == null) {
                    sInstance = new RepositoryManager();
                }
            }
        }

        return sInstance;
    }

    private RepositoryManager() {
    }

    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    private RxErrorHandler mRxErrorHandler;

    /** 必须设置 */
    public RepositoryManager setOkHttpClient(@NonNull OkHttpClient okHttpClient) {
        Preconditions.checkNotNull(okHttpClient, "OkHttpClient can't be empty");
        this.mOkHttpClient = okHttpClient;
        return this;
    }

    public RepositoryManager setRetrofit(@NonNull Retrofit.Builder builder) {
        Preconditions.checkNotNull(mOkHttpClient, "OkHttpClient can't be empty");
        this.mRetrofit = builder
                .client(mOkHttpClient)
                .build();

        return this;
    }

    public RepositoryManager setRxErrorHandler(RxErrorHandler rxErrorHandler) {
        this.mRxErrorHandler = rxErrorHandler;
        return this;
    }

    @Override
    public RxErrorHandler getRxErrorHandler() {
        Preconditions.checkNotNull(mRxErrorHandler, "RxErrorHandler is null");
        return mRxErrorHandler;
    }

    @NonNull
    @Override
    public synchronized  <T> T obtainRetrofitService(@NonNull Class<T> serviceClass) {
        return createWrapperService(serviceClass);
    }

    /**
     * 根据 https://zhuanlan.zhihu.com/p/40097338 对 Retrofit 进行的优化
     *
     * @param serviceClass ApiService class
     * @param <T> ApiService class
     * @return ApiService
     */
    private <T> T createWrapperService(Class<T> serviceClass) {
        Preconditions.checkNotNull(serviceClass, "serviceClass == null");

        // 二次代理
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(),
                new Class<?>[]{serviceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, @Nullable Object[] args)
                            throws Throwable {
                        // 此处在调用 serviceClass 中的方法时触发

                        if (method.getReturnType() == Observable.class) {
                            // 如果方法返回值是 Observable 的话，则包一层再返回，
                            // 只包一层 defer 由外部去控制耗时方法以及网络请求所处线程，
                            // 如此对原项目的影响为 0，且更可控。
                            return Observable.defer(() -> {
                                final T service = getRetrofitService(serviceClass);
                                // 执行真正的 Retrofit 动态代理的方法
                                return ((Observable) getRetrofitMethod(service, method)
                                        .invoke(service, args));
                            });
                        } else if (method.getReturnType() == Single.class) {
                            // 如果方法返回值是 Single 的话，则包一层再返回。
                            return Single.defer(() -> {
                                final T service = getRetrofitService(serviceClass);
                                // 执行真正的 Retrofit 动态代理的方法
                                return ((Single) getRetrofitMethod(service, method)
                                        .invoke(service, args));
                            });
                        }

                        // 返回值不是 Observable 或 Single 的话不处理。
                        final T service = getRetrofitService(serviceClass);
                        return getRetrofitMethod(service, method).invoke(service, args);
                    }
                });
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass ApiService class
     * @param <T> ApiService class
     * @return ApiService
     */
    private <T> T getRetrofitService(Class<T> serviceClass) {
        Preconditions.checkNotNull(mRetrofit, "Retrofit can not be empty");
        return mRetrofit.create(serviceClass);
    }

    private <T> Method getRetrofitMethod(T service, Method method) throws NoSuchMethodException {
        return service.getClass().getMethod(method.getName(), method.getParameterTypes());
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new NetworkInterceptor())
                .addInterceptor(new RequestInterceptor(RequestInterceptor.Level.ALL, new DefaultFormatPrinter()))
                .build();

        return client;
    }

    private Retrofit getRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder.baseUrl("")
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
