package com.mcgrady.news.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mcgrady.common_core.base.BaseActivity;
import com.mcgrady.common_core.di.component.AppComponent;
import com.mcgrady.common_core.intergration.manager.AppManager;
import com.mcgrady.common_core.utils.Preconditions;
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

import java.util.List;

import butterknife.BindView;

public class ZhihuHomeActivity extends BaseActivity<ZhihuHomePresenter> implements ZhihuHomeContract.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R2.id.news_refresh_zhihu)
    SmartRefreshLayout refreshLayout;
    @BindView(R2.id.news_recycler_zhihu)
    RecyclerView recyclerView;

    private ZhihuhomeAdapter adapter;

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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ViewUtils.configRecyclerView(recyclerView, layoutManager);

        adapter = new ZhihuhomeAdapter(this);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        adapter.bindToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.autoRefresh();
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
    public void loadMoreData(List<DailyListBean.StoriesBean> list) {
        adapter.addData(list);
        refreshLayout.finishLoadMore();
    }
}
