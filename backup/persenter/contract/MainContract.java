package com.mcgrady.main.persenter.contract;

import com.mcgrady.core.base.IBaseView;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/9/18
 */

public interface MainContract {

    interface IView extends IBaseView {

    }

    interface IPresenter {

        /**
         * 加载单独启动页图片
         */
        void loadOwspacePicList();
    }

    interface IModule {

    }
}
