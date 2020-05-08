package com.mcgrady.news.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.mcgrady.common_core.imageEngine.config.CommonImageConfigImpl;
import com.mcgrady.news.mvp.model.entity.ZhihuDailyStoriesBean;
import com.mcgrady.xskeleton.http.imageloader.ImageLoader;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * Created by mcgrady on 2020/5/7.
 */
public class ZhihuDailyTopStoriesAdapter extends BannerAdapter<ZhihuDailyStoriesBean.TopStoriesBean, BaseViewHolder> {

    private ImageLoader imageLoader;

    public ZhihuDailyTopStoriesAdapter(ImageLoader imageLoader, List<ZhihuDailyStoriesBean.TopStoriesBean> datas) {
        super(datas);
        this.imageLoader = imageLoader;
    }

    @Override
    public BaseViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        return new BaseViewHolder(imageView);
    }

    @Override
    public void onBindView(BaseViewHolder holder, ZhihuDailyStoriesBean.TopStoriesBean data, int position, int size) {
        imageLoader.loadImage(holder.itemView.getContext(), CommonImageConfigImpl.builder()
                .url(data.getImage())
                .imageView((ImageView) holder.itemView)
                .build());
    }
}
