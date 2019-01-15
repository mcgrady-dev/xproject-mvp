package com.mcgrady.news.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mcgrady.common_core.di.component.AppComponent;
import com.mcgrady.common_core.http.imageloader.ImageLoader;
import com.mcgrady.common_core.intergration.config.CommonImageConfigImpl;
import com.mcgrady.common_core.utils.Utils;
import com.mcgrady.common_res.interf.IViewHolderRelease;
import com.mcgrady.news.R;
import com.mcgrady.news.mvp.model.entity.DailyListBean;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/15
 */

public class ZhihuhomeAdapter extends BaseQuickAdapter<DailyListBean.StoriesBean, BaseViewHolder>
        implements IViewHolderRelease<BaseViewHolder> {

    private AppComponent appComponent;
    private ImageLoader imageLoader;

    public ZhihuhomeAdapter() {
        super(R.layout.news_item_zhihu_home, null);

        appComponent = Utils.obtainAppComponentFromContext(mContext);
        imageLoader = appComponent.imageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, DailyListBean.StoriesBean item) {
        helper.setText(R.id.news_tv_name, item.getTitle());


    }

    @Override
    public void onRelease(BaseViewHolder helper) {
        imageLoader.clear(appComponent.application(), CommonImageConfigImpl.builder()
                .imageView(helper.getView(R.id.news_iv_avatar))
                .build());
    }
}
