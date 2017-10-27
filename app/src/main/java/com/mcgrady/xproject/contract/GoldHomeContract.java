package com.mcgrady.xproject.contract;

import com.mcgrady.xproject.base.BasePresenter;
import com.mcgrady.xproject.base.BaseView;
import com.mcgrady.xproject.model.bean.GoldManagerBean;
import com.mcgrady.xproject.model.bean.GoldManagerItemBean;

import java.util.List;

/**
 * Created by mcgrady on 2017/8/30.
 */

public interface GoldHomeContract {

    interface View extends BaseView {

        void updateTab(List<GoldManagerItemBean> list);

        void jumpToManager(GoldManagerBean bean);
    }

    interface Presenter extends BasePresenter<View> {

        void initManagerList();

        void setManagerList();

    }

}
