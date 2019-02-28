package com.mcgrady.news.mvp.contract;

import com.mcgrady.common_core.mvp.IModel;
import com.mcgrady.common_core.mvp.IView;
import com.mcgrady.news.mvp.model.entity.ZhihuDailyDetailBean;

import io.reactivex.Observable;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/2/28
 */

public interface ZhihuDailyDetailContract {

    interface View extends IView {

        void setDailyDetail(ZhihuDailyDetailBean bean);
    }

    interface Model extends IModel {

        Observable<ZhihuDailyDetailBean> getDailyDetail(int dailyId);
    }
}
