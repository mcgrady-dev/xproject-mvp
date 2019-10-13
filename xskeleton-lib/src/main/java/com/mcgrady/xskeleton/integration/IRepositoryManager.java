package com.mcgrady.xskeleton.integration;

import androidx.annotation.NonNull;
import com.mcgrady.xskeleton.http.handler.RxErrorHandler;

/**
 * Created by mcgrady on 2019-08-10.
 */
public interface IRepositoryManager {

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param service Retrofit service class
     * @param <T>     Retrofit service 类型
     * @return Retrofit service
     */
    @NonNull
    <T> T obtainRetrofitService(@NonNull Class<T> service);

    RxErrorHandler getRxErrorHandler();
}
