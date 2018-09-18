package com.mcgrady.main.module;

import com.mcgrady.main.module.bean.SplashPicBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/9/18
 */

public interface ISplashApi {

    String baseUrl = "http://static.owspace.com/";

    @GET("static/picture_list.txt")
    Flowable<SplashPicBean> getOwspacePicList(@Query("client") String client,
                                                                 @Query("version") String version,
                                                                 @Query("time") Long time,
                                                                 @Query("device_id") String deviceId);


}
