package com.mcgrady.news.mvp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mcgrady.common_res.interf.IViewHolderRelease;
import com.mcgrady.news.R;
import com.mcgrady.news.mvp.model.entity.ZhihuDailyMultipleItem;
import com.mcgrady.news.mvp.model.entity.ZhihuDailyStoriesBean;
import com.youth.banner.loader.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/15
 */

public class ZhihuDailyHomeAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>
        implements IViewHolderRelease<BaseViewHolder> {

    public static final int TYPE_HEADER = 1;
    public static final int TYPE_DATE = 2;
    public static final int TYPE_ITEM = 3;

    private ImageLoader imageLoader;

    public ZhihuDailyHomeAdapter(Context context) {
        this(context, new ArrayList());
    }
    public ZhihuDailyHomeAdapter(Context context, List data) {
        super(data);
        addItemType(TYPE_HEADER, R.layout.news_header_banner);
        addItemType(TYPE_DATE, R.layout.news_item_zhihu_daily_date);
        addItemType(TYPE_ITEM, R.layout.news_item_zhihu_daily_home);

//        imageLoader = appComponent.imageLoader();
    }

    public void setData(ZhihuDailyStoriesBean data) {
        List<MultiItemEntity> entities = new ArrayList<>();

        entities.add(new ZhihuDailyMultipleItem(TYPE_DATE, data.getDate()));

        List<ZhihuDailyStoriesBean.StoriesBean> stories = data.getStories();
        for (ZhihuDailyStoriesBean.StoriesBean storiesBean : stories) {
            entities.add(new ZhihuDailyMultipleItem(TYPE_ITEM, storiesBean));
        }

        setNewData(entities);
    }

    public void addData(ZhihuDailyStoriesBean data) {
        List<MultiItemEntity> entities = new ArrayList<>();

        entities.add(new ZhihuDailyMultipleItem(TYPE_DATE, data.getDate()));

        List<ZhihuDailyStoriesBean.StoriesBean> stories = data.getStories();
        for (ZhihuDailyStoriesBean.StoriesBean storiesBean : stories) {
            entities.add(new ZhihuDailyMultipleItem(TYPE_ITEM, storiesBean));
        }

        addData(entities);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        int itemType = item.getItemType();
        switch (itemType) {
            case TYPE_DATE:
                helper.setText(R.id.news_tv_date, getDateTitle((String) ((ZhihuDailyMultipleItem) item).getData()));
                break;
            case TYPE_ITEM:
                ZhihuDailyStoriesBean.StoriesBean storiesBean = (ZhihuDailyStoriesBean.StoriesBean) ((ZhihuDailyMultipleItem) item).getData();

                helper.setText(R.id.list_item_title, storiesBean.getTitle());
                List<String> imageList = storiesBean.getImages();
//                imageLoader.loadImage(mContext, ImageConfigImpl.builder()
//                        .url(imageList == null || imageList.isEmpty() ? "" : imageList.get(0))
//                        .imageView(helper.getView(R.id.list_item_image))
//                        .build());
                helper.setGone(R.id.list_item_multipic, imageList != null && !imageList.isEmpty() && imageList.size() > 1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRelease(BaseViewHolder helper) {
//        imageLoader.clear(appComponent.application(), ImageConfigImpl.builder()
//                .imageView(helper.getView(R.id.list_item_image))
//                .build());
    }

    public String getDateTitle(String serviceTime) {
        if (TextUtils.isEmpty(serviceTime)) {
            return "";
        }

        Date date = TimeUtils.string2Date(serviceTime, new SimpleDateFormat("yyyyMMdd"));
        if (TimeUtils.isToday(date)) {
            return "今日热文";
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append(TimeUtils.date2String(date, new SimpleDateFormat("MM月dd日", Locale.getDefault())));
            builder.append(" ");
            builder.append(TimeUtils.getChineseWeek(date));
            return  builder.toString();
        }
    }
}
