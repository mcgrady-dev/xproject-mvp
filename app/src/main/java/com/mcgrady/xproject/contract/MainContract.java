package com.mcgrady.xproject.contract;

import com.mcgrady.xproject.base.BasePresenter;
import com.mcgrady.xproject.base.BaseView;

/**
 * Created by mcgrady on 2017/8/9.
 */

public interface MainContract {

    interface View extends BaseView {

        void showTestDialog(String msg);
    }

    interface Presenter extends BasePresenter<View> {

        void testPresenter(String msg);
    }
}
