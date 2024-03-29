package com.mcgrady.xskeleton.integration;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mcgrady.xskeleton.cache.Cache;
import com.mcgrady.xskeleton.cache.CacheType;
import com.mcgrady.xskeleton.utils.Preconditions;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Retrofit;

/**
 * Created by mcgrady on 2019-08-10.
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {

    @Inject
    Lazy<Retrofit> retrofit;
    @Inject
    Application application;
    @Inject
    Cache.Factory cacheFactory;
//    @Inject
//    RxCache rxCache;

    private Cache<String, Object> retrofitServiceCache;
    private Cache<String, Object> cacheServiceCache;

    @Inject
    public RepositoryManager() {
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

    private <T> Method getRetrofitMethod(T service, Method method) throws NoSuchMethodException {
        return service.getClass().getMethod(method.getName(), method.getParameterTypes());
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass ApiService class
     * @param <T> ApiService class
     * @return ApiService
     */
    private <T> T getRetrofitService(Class<T> serviceClass) {
        if (retrofitServiceCache == null) {
            retrofitServiceCache = cacheFactory.build(CacheType.RETROFIT_SERVICE_CACHE);
        }
        Preconditions.checkNotNull(retrofitServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
        T retrofitService = (T) retrofitServiceCache.get(serviceClass.getCanonicalName());
        if (retrofitService == null) {
            retrofitService = retrofit.get().create(serviceClass);
            retrofitServiceCache.put(serviceClass.getCanonicalName(), retrofitService);
        }
        return retrofitService;
    }

    @NonNull
    @Override
    public <T> T obtainCacheService(@NonNull Class<T> cache) {
//        Preconditions.checkNotNull(cache, "cacheClass == null");
//        if (cacheServiceCache == null) {
//            cacheServiceCache = cacheFactory.build(CacheType.CACHE_SERVICE_CACHE);
//        }
//        Preconditions.checkNotNull(cacheServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
//        T cacheService = (T) cacheServiceCache.get(cache.getCanonicalName());
//        if (cacheService == null) {
//            cacheService = rxCache.using(cache);
//            cacheServiceCache.put(cache.getCanonicalName(), cacheService);
//        }
//        return cacheService;

        return null;
    }

    @Override
    public void clearAllCache() {
//        rxCache.get().evictAll().subscribe();
    }

    @NonNull
    @Override
    public Context getContext() {
        return application;
    }
}
