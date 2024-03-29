package com.mcgrady.xproject.zhihu.mvp.model.api;

import com.mcgrady.common_core.http.BaseResponse;
import com.mcgrady.xproject.zhihu.mvp.model.entity.ZhihuDailyDetailBean;
import com.mcgrady.xproject.zhihu.mvp.model.entity.ZhihuDailyStoriesBean;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import static com.mcgrady.common_core.http.manager.RetrofitUrlManager.DOMAIN_NAME_HEADER;


/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于 zhihu 的一些 API
 * <p>
 * Created by JessYan on 08/05/2016 12:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface ZhihuService {
    /**
     * 最新日报
     */
    @Headers({DOMAIN_NAME_HEADER + Api.ZHIHU_DOMAIN_NAME})
    @GET("/api/4/news/latest")
    Observable<ZhihuDailyStoriesBean> getDailyList();

    @Headers({DOMAIN_NAME_HEADER + Api.ZHIHU_DOMAIN_NAME})
    @GET("/api/4/news/latest")
    Observable<BaseResponse<ZhihuDailyStoriesBean>> getDailyList2();

    /**
     * 往期日报
     */
    @Headers({DOMAIN_NAME_HEADER + Api.ZHIHU_DOMAIN_NAME})
    @GET("/api/4/news/before/{date}")
    Observable<ZhihuDailyStoriesBean> getBeforeDailyList(@Path("date") String date);

    /**
     * 日报详情
     */
    @Headers({DOMAIN_NAME_HEADER + Api.ZHIHU_DOMAIN_NAME})
    @GET("/api/4/news/{id}")
    Observable<ZhihuDailyDetailBean> getDailyDetail(@Path("id") int id);
}
