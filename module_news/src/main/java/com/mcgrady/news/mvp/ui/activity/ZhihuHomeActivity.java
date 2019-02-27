package com.mcgrady.news.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hjq.toast.ToastUtils;
import com.mcgrady.common_core.base.BaseActivity;
import com.mcgrady.common_core.config.RouterHub;
import com.mcgrady.common_core.di.component.AppComponent;
import com.mcgrady.common_core.http.imageloader.ImageConfigImpl;
import com.mcgrady.common_core.utils.Utils;
import com.mcgrady.common_res.utils.ViewUtils;
import com.mcgrady.news.R;
import com.mcgrady.news.R2;
import com.mcgrady.news.di.component.DaggerZhihuHomeComponent;
import com.mcgrady.news.mvp.contract.ZhihuHomeContract;
import com.mcgrady.news.mvp.model.entity.DailyMultipleItem;
import com.mcgrady.news.mvp.model.entity.DailyStoriesBean;
import com.mcgrady.news.mvp.presenter.ZhihuHomePresenter;
import com.mcgrady.news.mvp.ui.adapter.ZhihuhomeAdapter;
import com.mcgrady.xtitlebar.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

@Route(path = RouterHub.ZHIHU_HOME)
public class ZhihuHomeActivity extends BaseActivity<ZhihuHomePresenter> implements ZhihuHomeContract.View,
        OnRefreshListener, OnLoadMoreListener, OnBannerListener {

    @BindView(R2.id.news_titlebar_zhihu_home)
    TitleBar titleBar;
    @BindView(R2.id.news_refresh_zhihu)
    SmartRefreshLayout refreshLayout;
    @BindView(R2.id.news_recycler_zhihu)
    RecyclerView recyclerView;

    private Banner banner;
    private ZhihuhomeAdapter adapter;
    private LinearLayoutManager linearManager;
    private int lastTitlePostion = -1;

    private AppComponent mAppComponent;
    private com.mcgrady.common_core.http.imageloader.ImageLoader mImageLoader;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerZhihuHomeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.news_activity_zhihu_home;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mAppComponent = Utils.obtainAppComponentFromContext(this);
        mImageLoader = mAppComponent.imageLoader();

        linearManager = new LinearLayoutManager(this);
        ViewUtils.configRecyclerView(recyclerView, linearManager);

        adapter = new ZhihuhomeAdapter(this);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build(RouterHub.ZHIHU_DAILY_DETAIL)
                        .withInt("daily_id", 0)
                        .withString("daily_title", "")
                        .navigation(ZhihuHomeActivity.this);
            }
        });

        adapter.bindToRecyclerView(recyclerView);

        banner = (Banner) LayoutInflater.from(this).inflate(R.layout.news_header_banner, null);
        banner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight() / 3));
        adapter.setHeaderView(banner);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                changeToolbarTitle(dy);
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.autoRefresh();
    }

    private void changeToolbarTitle(int dy) {
        int position = linearManager.findFirstVisibleItemPosition();
        if (lastTitlePostion == position) {
            return;
        }

        int itemType = adapter.getItemViewType(position);
        if (BaseQuickAdapter.HEADER_VIEW == itemType) {
            titleBar.setTitle("首页");
        } else if (dy > 0 && ZhihuhomeAdapter.TYPE_DATE == itemType) {
            // postion - 1 是因为adapter有headerview
            DailyMultipleItem<String> dateItem = (DailyMultipleItem<String>) adapter.getItem(position - 1);
            titleBar.setTitle(adapter.getDateTitle(dateItem.getData()));
        } else if (dy < 0) {
            List<MultiItemEntity> subList = new ArrayList<>();
            subList.addAll(adapter.getData().subList(0, position));
            Collections.reverse(subList);
            for (MultiItemEntity itemEntity : subList) {
                if (ZhihuhomeAdapter.TYPE_DATE == itemEntity.getItemType()) {
                    titleBar.setTitle(adapter.getDateTitle((String) ((DailyMultipleItem) itemEntity).getData()));
                    break;
                }
            }
        }
        lastTitlePostion = position;
    }



    @Override
    protected void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestDailyList();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestBeforeDailyList();
    }

    @Override
    protected void onDestroy() {
        ViewUtils.releaseAllHolder(recyclerView);
        super.onDestroy();
    }

    @Override
    public void finishLoadMore(boolean success) {
        refreshLayout.finishLoadMore(success);
    }

    @Override
    public void notifyDataSetChanged(DailyStoriesBean data) {
        banner.setImages(data.getTop_stories())
            .setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object bean, ImageView imageView) {
                    mImageLoader.loadImage(ZhihuHomeActivity.this,
                            ImageConfigImpl.builder()
                                    .url(((DailyStoriesBean.TopStoriesBean) bean).getImage())
                                    .imageView(imageView)
                                    .build());
                }
            })
            .setOnBannerListener(ZhihuHomeActivity.this)
            .start();
        adapter.setData(data);
        refreshLayout.finishRefresh();
    }

    @Override
    public void loadMoreData(DailyStoriesBean data) {
        adapter.addData(data);
        refreshLayout.finishLoadMore();
    }

    @Override
    public void OnBannerClick(int position) {
        ToastUtils.show("你点击了: " + position);
    }
}
