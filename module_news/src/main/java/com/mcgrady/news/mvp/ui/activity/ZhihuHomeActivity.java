package com.mcgrady.news.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
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

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.toast.ToastUtils;
import com.mcgrady.common_core.base.BaseActivity;
import com.mcgrady.common_core.di.component.AppComponent;
import com.mcgrady.common_core.http.imageloader.ImageConfigImpl;
import com.mcgrady.common_core.intergration.manager.AppManager;
import com.mcgrady.common_core.utils.Preconditions;
import com.mcgrady.common_core.utils.Utils;
import com.mcgrady.common_res.utils.ViewUtils;
import com.mcgrady.news.R;
import com.mcgrady.news.R2;
import com.mcgrady.news.di.component.DaggerZhihuHomeComponent;
import com.mcgrady.news.mvp.contract.ZhihuHomeContract;
import com.mcgrady.news.mvp.model.entity.DailyListBean;
import com.mcgrady.news.mvp.presenter.ZhihuHomePresenter;
import com.mcgrady.news.mvp.ui.adapter.ZhihuhomeAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

import butterknife.BindView;

public class ZhihuHomeActivity extends BaseActivity<ZhihuHomePresenter> implements ZhihuHomeContract.View, OnRefreshListener, OnLoadMoreListener, OnBannerListener {

    @BindView(R2.id.news_refresh_zhihu)
    SmartRefreshLayout refreshLayout;
    @BindView(R2.id.news_recycler_zhihu)
    RecyclerView recyclerView;

    private Banner banner;
    private ZhihuhomeAdapter adapter;

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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ViewUtils.configRecyclerView(recyclerView, layoutManager);

        adapter = new ZhihuhomeAdapter(this);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        View header = LayoutInflater.from(this).inflate(R.layout.news_header_banner, null);
        banner = header.findViewById(R.id.news_banner);
        banner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight() / 4));
        adapter.setHeaderView(header);

        adapter.bindToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.autoRefresh();
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
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        Preconditions.checkNotNull(message);
        AppManager.getAppManager().showSnackbar(message, false);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        Preconditions.checkNotNull(intent);
        AppManager.getAppManager().startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        ViewUtils.releaseAllHolder(recyclerView);
        super.onDestroy();
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void notifyDataSetChanged(List<DailyListBean.StoriesBean> list) {
        adapter.setNewData(list);
        refreshLayout.finishRefresh();
    }

    @Override
    public void setBanner(List<DailyListBean.TopStoriesBean> topList) {
        banner.setImages(topList)
            .setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object bean, ImageView imageView) {
                    mImageLoader.loadImage(ZhihuHomeActivity.this,
                        ImageConfigImpl.builder()
                            .url(((DailyListBean.TopStoriesBean) bean).getImage())
                            .imageView(imageView)
                            .build());
                }
            })
            .setOnBannerListener(ZhihuHomeActivity.this)
            .start();
    }

    @Override
    public void loadMoreData(List<DailyListBean.StoriesBean> list) {
        adapter.addData(list);
        refreshLayout.finishLoadMore();
    }

    @Override
    public void OnBannerClick(int position) {
        ToastUtils.show("你点击了: " + position);
    }
}
