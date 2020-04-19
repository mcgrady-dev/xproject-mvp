package com.mcgrady.xskeleton.integration;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.mcgrady.xskeleton.cache.Cache;
import com.mcgrady.xskeleton.cache.CacheType;
import com.mcgrady.xskeleton.base.AppComponent;
import com.mcgrady.xskeleton.utils.Preconditions;

import java.lang.reflect.Proxy;

import retrofit2.Retrofit;

/**
 * Created by mcgrady on 2019-08-10.
 */
public class RepositoryManager implements IRepositoryManager {

    private Retrofit retrofit;
//    private RxCache rxCache;
    private Cache.Factory cacheFactory;
    private Cache<String, Object> retrofitServiceCache;
    private Cache<String, Object> cacheServiceCache;
    private ObtainServiceDelegate obtainServiceDelegate;

    private static Application sApplication;
    private volatile static boolean sHasInit = false;
    private volatile static RepositoryManager sInstance;

    /**
     * Init, it must be call before used router.
     */
    public static void init(Application application) {
        if (!sHasInit) {
            sApplication = application;
            sHasInit = true;
        }
    }

    public static RepositoryManager getInstance() {
        if (!sHasInit) {
            throw new RuntimeException("RepositoryManager::Init::Invoke init(context) first!");
        } else {
            if (sInstance == null) {
                synchronized (RepositoryManager.class) {
                    if (sInstance == null) {
                        sInstance = new RepositoryManager();
                    }
                }
            }

            return sInstance;
        }
    }

    private RepositoryManager() {
        retrofit = AppComponent.obtainClientModule(sApplication).getRetrofit();
        cacheFactory = AppComponent.obtainAppModule(sApplication).getCacheFactory();
    }

    @NonNull
    @Override
    public synchronized  <T> T obtainRetrofitService(@NonNull Class<T> serviceClass) {
        if (retrofitServiceCache == null) {
            retrofitServiceCache = cacheFactory.build(CacheType.RETROFIT_SERVICE_CACHE);
        }
        Preconditions.checkNotNull(retrofitServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
        T retrofitService = (T) retrofitServiceCache.get(serviceClass.getCanonicalName());
        if (retrofitService == null) {
            if (obtainServiceDelegate != null) {
                retrofitService = obtainServiceDelegate.createRetrofitService(
                        retrofit, serviceClass);
            }
            if (retrofitService == null) {
                retrofitService = (T) Proxy.newProxyInstance(
                        serviceClass.getClassLoader(),
                        new Class[]{serviceClass},
                        new RetrofitServiceProxyHandler(retrofit, serviceClass));
            }
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
//        rxCache.evictAll().subscribe();
    }

    @NonNull
    @Override
    public Context getContext() {
        return sApplication;
    }
}
