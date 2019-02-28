package com.mcgrady.news.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jaeger.library.StatusBarUtil;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.NestedScrollAgentWebView;
import com.mcgrady.common_core.base.BaseActivity;
import com.mcgrady.common_core.config.RouterHub;
import com.mcgrady.common_core.di.component.AppComponent;
import com.mcgrady.common_core.http.imageloader.ImageConfigImpl;
import com.mcgrady.common_core.http.imageloader.ImageLoader;
import com.mcgrady.common_core.utils.HtmlUtils;
import com.mcgrady.common_core.utils.Utils;
import com.mcgrady.news.R;
import com.mcgrady.news.R2;
import com.mcgrady.news.di.component.DaggerZhihuDailyDetailComponent;
import com.mcgrady.news.mvp.contract.ZhihuDailyDetailContract;
import com.mcgrady.news.mvp.model.entity.ZhihuDailyDetailBean;
import com.mcgrady.news.mvp.presenter.ZhihuDailyDetailPresenter;

import butterknife.BindView;

/**
 * 知乎日报详情
 */
@Route(path = RouterHub.ZHIHU_DAILY_DETAIL)
public class ZhihuDailyDetailActivity extends BaseActivity<ZhihuDailyDetailPresenter> implements ZhihuDailyDetailContract.View {

    @BindView(R2.id.news_iv_daily_header)
    ImageView ivDailyHeader;
    @BindView(R2.id.news_tv_daily_title)
    TextView tvDailyTitle;
    @BindView(R2.id.news_tv_img_source)
    TextView tvImgSource;
    @BindView(R2.id.news_cl_container)
    CoordinatorLayout coordinatorLayout;

    private AppComponent appComponent;
    private ImageLoader imageLoader;
    private AgentWeb agentWeb;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerZhihuDailyDetailComponent
            .builder()
            .appComponent(appComponent)
            .view(this)
            .build()
            .inject(this);

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarUtil.setTransparent(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        StatusBarUtil.setTransparent(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.news_activity_zhihu_daily_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        appComponent = Utils.obtainAppComponentFromContext(this);
        imageLoader = appComponent.imageLoader();

        int dailyId = getIntent().getIntExtra("daily_id", -1);
        String dailyTitle = getIntent().getStringExtra("daily_title");
        String dailyImgUrl = getIntent().getStringExtra("daily_img_url");

        tvDailyTitle.setText(dailyTitle);

        imageLoader.loadImage(this, ImageConfigImpl.builder()
                .url(dailyImgUrl)
                .isCropCenter(true)
                .imageView(ivDailyHeader)
                .build());

        if (mPresenter != null) {
            mPresenter.requestDailyDetail(dailyId);
        }
    }

    @Override
    public void setDailyDetail(ZhihuDailyDetailBean bean) {

        tvImgSource.setText(bean.getImage_source());

        String htmlUrl = HtmlUtils.createHtmlData(bean.getBody(), bean.getCss(), bean.getJs());

        NestedScrollAgentWebView webView = new NestedScrollAgentWebView(this);
        CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(-1, -1);
        lp.setBehavior(new AppBarLayout.ScrollingViewBehavior());

        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(coordinatorLayout,  1, lp)
                .closeIndicator()
                .setWebView(webView)
                .createAgentWeb()
                .ready()
                .go("");
        agentWeb.getUrlLoader().loadData(htmlUrl, HtmlUtils.MIME_TYPE, "UTF-8");
    }
}
