package com.mcgrady.xproject.zhihu.mvp.contract;

import com.mcgrady.common_core.http.BaseResponse;
import com.mcgrady.xproject.zhihu.mvp.model.entity.ZhihuDailyStoriesBean;
import com.mcgrady.xskeleton.base.IModel;
import com.mcgrady.xskeleton.base.IView;

import io.reactivex.Observable;

public interface ZhihuDailyHomeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void notifyDataSetChanged(ZhihuDailyStoriesBean data);

        void loadMoreData(ZhihuDailyStoriesBean data);

        void finishRefresh();

        void finishLoadMore(boolean success);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<ZhihuDailyStoriesBean> getDailyList();

        //基于BaseResponse壳的用法
        Observable<BaseResponse<ZhihuDailyStoriesBean>> getDailList2();

        Observable<ZhihuDailyStoriesBean> getBeforeDailyList(String date);
    }
}
