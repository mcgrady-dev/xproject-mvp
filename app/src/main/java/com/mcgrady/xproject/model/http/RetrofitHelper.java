package com.mcgrady.xproject.model.http;

import com.mcgrady.xproject.app.Constants;
import com.mcgrady.xproject.model.bean.GoldListBean;
import com.mcgrady.xproject.model.http.api.GoldApis;
import com.mcgrady.xproject.model.http.response.GoldHttpResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by mcgrady on 2017/10/23.
 */

public class RetrofitHelper implements HttpHelper {

    private GoldApis mGoldApis;

    @Inject
    public RetrofitHelper(GoldApis goldApis) {
        this.mGoldApis = goldApis;
    }


    @Override
    public Flowable<GoldHttpResponse<List<GoldListBean>>> fetchGoldList(String type, int num, int page) {
        return mGoldApis.getGoldList(Constants.LEANCLOUD_ID, Constants.LEANCLOUD_SIGN,
                "{\"category\":\"" + type + "\"}", "-createdAt", "user,user.installation", num, page * num);
    }

    @Override
    public Flowable<GoldHttpResponse<List<GoldListBean>>> fetchGoldHotList(String type, String dataTime, int limit) {
        return mGoldApis.getGoldHot(Constants.LEANCLOUD_ID, Constants.LEANCLOUD_SIGN,
                "{\"category\":\"" + type + "\",\"createdAt\":{\"$gt\":{\"__type\":\"Date\",\"iso\":\"" + dataTime + "T00:00:00.000Z\"}},\"objectId\":{\"$nin\":[\"58362f160ce463005890753e\",\"583659fcc59e0d005775cc8c\",\"5836b7358ac2470065d3df62\"]}}",
                "-hotIndex", "user,user.installation", limit);
    }
}
