package com.mcgrady.xproject.model.http;

import com.mcgrady.xproject.model.bean.GoldListBean;
import com.mcgrady.xproject.model.http.response.GoldHttpResponse;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by mcgrady on 2017/10/23.
 */

public interface HttpHelper {


    Flowable<GoldHttpResponse<List<GoldListBean>>> fetchGoldList(String type, int num, int page);

    Flowable<GoldHttpResponse<List<GoldListBean>>> fetchGoldHotList(String type, String dataTime, int limit);

}
