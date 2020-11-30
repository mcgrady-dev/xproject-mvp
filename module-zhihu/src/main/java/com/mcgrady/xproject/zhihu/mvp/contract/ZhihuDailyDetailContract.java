package com.mcgrady.zhihu.mvp.contract;

import com.mcgrady.zhihu.mvp.model.entity.ZhihuDailyDetailBean;
import com.mcgrady.xskeleton.base.IModel;
import com.mcgrady.xskeleton.base.IView;

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
