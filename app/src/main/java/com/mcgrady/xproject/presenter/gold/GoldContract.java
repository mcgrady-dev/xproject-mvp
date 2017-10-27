package com.mcgrady.xproject.presenter.gold;

import com.mcgrady.xproject.base.BasePresenter;
import com.mcgrady.xproject.base.BaseView;
import com.mcgrady.xproject.model.bean.GoldListBean;

import java.util.List;

/**
 * Created by mcgrady on 2017/10/23.
 */

public interface GoldContract {

    interface View extends BaseView {
        void showContent(List<GoldListBean> goldListBean);

        void showMoreContent(List<GoldListBean> goldMoreListBean, int start, int end);
    }

    interface Presenter extends BasePresenter<View> {

        void getGoldData(String type);

        void getMoreGoldData();
    }

}
