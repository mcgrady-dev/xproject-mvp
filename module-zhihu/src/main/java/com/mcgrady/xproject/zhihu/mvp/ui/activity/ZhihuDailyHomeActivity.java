package com.mcgrady.xproject.zhihu.mvp.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mcgrady.common_core.RouterHub;
import com.mcgrady.common_core.base.BaseActivity;
import com.mcgrady.common_core.utils.ViewUtils;
import com.mcgrady.module_test.IMyAidlInterface;
import com.mcgrady.xproject.zhihu.R;
import com.mcgrady.xproject.zhihu.R2;
import com.mcgrady.xproject.zhihu.mvp.contract.ZhihuDailyHomeContract;
import com.mcgrady.xproject.zhihu.mvp.model.ZhihuModel;
import com.mcgrady.xproject.zhihu.mvp.model.entity.ZhihuDailyMultipleItem;
import com.mcgrady.xproject.zhihu.mvp.model.entity.ZhihuDailyStoriesBean;
import com.mcgrady.xproject.zhihu.mvp.presenter.ZhihuDailyHomePresenter;
import com.mcgrady.xproject.zhihu.mvp.ui.adapter.ZhihuDailyHomeAdapter;
import com.mcgrady.xproject.zhihu.mvp.ui.adapter.ZhihuDailyTopStoriesAdapter;
import com.mcgrady.xskeleton.base.AppComponent;
import com.mcgrady.xskeleton.http.imageloader.ImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * 知乎日报列表
 */
@Route(path = RouterHub.ZHIHU_ACTIVITY_HOME)
public class ZhihuDailyHomeActivity extends BaseActivity<ZhihuDailyHomePresenter> implements ZhihuDailyHomeContract.View,
        OnRefreshListener, OnLoadMoreListener, OnBannerListener {

//    @BindView(R2.id.news_titlebar_zhihu_home)
//    TitleBar titleBar;
    @BindView(R2.id.zhihu_tv_date)
    TextView tvDate;
    @BindView(R2.id.zhihu_tv_month)
    TextView tvMonth;
    @BindView(R2.id.news_refresh_zhihu)
    SmartRefreshLayout refreshLayout;
    @BindView(R2.id.news_recycler_zhihu)
    RecyclerView recyclerView;

    private Banner banner;
    private ZhihuDailyHomeAdapter adapter;
    private LinearLayoutManager linearManager;
    private int lastTitlePostion = -1;
    private ImageLoader imageLoader;

    private IMyAidlInterface binder;

    @Override
    protected ZhihuDailyHomePresenter createPresenter() {
        ZhihuModel model = new ZhihuModel();
        return new ZhihuDailyHomePresenter(model, this, AppComponent.obtainClientModule(this).rxErrorHandler());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.news_activity_zhihu_daily_home;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        linearManager = new LinearLayoutManager(this);
        ViewUtils.configRecyclerView(recyclerView, linearManager);

        banner = (Banner) LayoutInflater.from(this).inflate(R.layout.news_header_banner, null);
        banner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight() / 3));
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        imageLoader = AppComponent.obtainAppModule(this).imageLoader();

        adapter = new ZhihuDailyHomeAdapter(this);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            ZhihuDailyMultipleItem itemEntity = (ZhihuDailyMultipleItem) adapter.getItem(position);
            switch (itemEntity.getItemType()) {
                case ZhihuDailyHomeAdapter.TYPE_ITEM:
                    ZhihuDailyStoriesBean.StoriesBean storiesBean = (ZhihuDailyStoriesBean.StoriesBean) itemEntity.getData();
                    ARouter.getInstance().build(RouterHub.ZHIHU_ACTIVITY_DAILY_DETAIL)
                            .withInt("daily_id", storiesBean.getId())
                            .withString("daily_title", storiesBean.getTitle())
                            .withString("daily_img_url", storiesBean.getImages() == null ?
                                    "" : storiesBean.getImages().get(0))
                            .navigation(ZhihuDailyHomeActivity.this);
                    break;
                default:
                    break;
            }
        });

        adapter.bindToRecyclerView(recyclerView);
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

        ServiceConnection conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = IMyAidlInterface.Stub.asInterface(service);
                try {
                    int c = binder.call(1, 2);
                    LogUtils.d("remote service: call result=" + c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                binder = null;
            }
        };

        Intent intent = new Intent("com.mcgrady.module_test.CalService");
        intent.setPackage("com.mcgrady.module_test");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        ToastUtils.showShort("xxxxxxxx");
        tvDate.postDelayed(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShort("xxxxxxxx");
                tvDate.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort("xxxxxxxx");
                    }
                }, 400);
            }
        }, 400);
//        ToastUtils.showShort("rrrrrrrr");
//        ToastUtils.showShort("wwwwwwww");
//        ToastUtils.showShort("hhhhhhhh");
//        ToastUtils.showShort("bbbbbbbb");

    }

    private void changeToolbarTitle(int dy) {
        int position = linearManager.findFirstVisibleItemPosition();
        if (lastTitlePostion == position) {
            return;
        }

        int itemType = adapter.getItemViewType(position);
        if (BaseQuickAdapter.HEADER_VIEW == itemType) {
//            titleBar.setTitle("首页");
        } else if (dy > 0 && ZhihuDailyHomeAdapter.TYPE_DATE == itemType) {
            // postion - 1 是因为adapter有headerview
            ZhihuDailyMultipleItem<String> dateItem = (ZhihuDailyMultipleItem<String>) adapter.getItem(position - 1);
//            titleBar.setTitle(adapter.getDateTitle(dateItem.getData()));
        } else if (dy < 0) {
            List<MultiItemEntity> subList = new ArrayList<>();
            subList.addAll(adapter.getData().subList(0, position));
            Collections.reverse(subList);
            for (MultiItemEntity itemEntity : subList) {
                if (ZhihuDailyHomeAdapter.TYPE_DATE == itemEntity.getItemType()) {
//                    titleBar.setTitle(adapter.getDateTitle((String) ((ZhihuDailyMultipleItem) itemEntity).getData()));
                    break;
                }
            }
        }
        lastTitlePostion = position;
    }


    @Override
    protected void onStart() {
        super.onStart();
        banner.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stop();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        presenter.requestDailyList();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        presenter.requestBeforeDailyList();
    }

    @Override
    protected void onDestroy() {
        ViewUtils.releaseAllHolder(recyclerView);
        super.onDestroy();
        banner.destroy();
    }

//    @Override
//    public void finishLoadMore(boolean success) {
//        refreshLayout.finishLoadMore(success);
//    }

    @Override
    public void notifyDataSetChanged(ZhihuDailyStoriesBean data) {
        banner.setAdapter(new ZhihuDailyTopStoriesAdapter(imageLoader, data.getTop_stories()))
                .setOnBannerListener(ZhihuDailyHomeActivity.this)
                .start();
        adapter.setData(data);
    }

    @Override
    public void loadMoreData(ZhihuDailyStoriesBean data) {
        adapter.addData(data);
    }

    @Override
    public void finishRefresh() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void finishLoadMore(boolean success) {
        refreshLayout.finishLoadMore(success);
    }

    @Override
    public void OnBannerClick(Object data, int position) {
//        ToastUtils.show("你点击了: " + position);
        SnackbarUtils.with(((ViewGroup)findViewById(android.R.id.content)).getChildAt(0))
                .setMessage("你点击了: " + position)
                .show();
    }
}
