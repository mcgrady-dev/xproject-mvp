package com.mcgrady.xproject;

import com.mcgrady.xproject.model.AllCity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mcgrady on 2017/10/18.
 */

public interface TestApi {

    @GET("citys")
    Observable<AllCity> getAllCity(@Query("key") String key);
}
