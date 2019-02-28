package com.mcgrady.news.mvp.contract;


import com.mcgrady.common_core.mvp.IModel;
import com.mcgrady.common_core.mvp.IView;
import com.mcgrady.news.mvp.model.entity.ZhihuDailyStoriesBean;

import io.reactivex.Observable;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/15/2019 13:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface ZhihuDailyHomeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void notifyDataSetChanged(ZhihuDailyStoriesBean data);

        void loadMoreData(ZhihuDailyStoriesBean data);

        void finishRefresh();

        void finishLoadMore();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<ZhihuDailyStoriesBean> getDailyList();

        Observable<ZhihuDailyStoriesBean> getBeforeDailyList(String date);
    }
}
