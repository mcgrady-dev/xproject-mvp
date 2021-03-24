package com.mcgrady.xproject.zhihu.mvp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.NestedScrollAgentWebView;
import com.mcgrady.common_core.RouterHub;
import com.mcgrady.common_core.base.BaseActivity;
import com.mcgrady.common_core.utils.HtmlUtils;
import com.mcgrady.xproject.zhihu.R;
import com.mcgrady.xproject.zhihu.R2;
import com.mcgrady.xproject.zhihu.mvp.contract.ZhihuDailyDetailContract;
import com.mcgrady.xproject.zhihu.mvp.model.entity.ZhihuDailyDetailBean;
import com.mcgrady.xproject.zhihu.mvp.presenter.ZhihuDailyDetailPresenter;
import com.mcgrady.xskeleton.http.imageloader.ImageLoader;

import butterknife.BindView;

/**
 * 知乎日报详情
 */
@Route(path = RouterHub.ZHIHU_ACTIVITY_DAILY_DETAIL)
public class ZhihuDailyDetailActivity extends BaseActivity<ZhihuDailyDetailPresenter> implements ZhihuDailyDetailContract.View {

    @BindView(R2.id.news_iv_daily_header)
    ImageView ivDailyHeader;
    @BindView(R2.id.news_tv_daily_title)
    TextView tvDailyTitle;
    @BindView(R2.id.news_tv_img_source)
    TextView tvImgSource;
    @BindView(R2.id.news_cl_container)
    CoordinatorLayout coordinatorLayout;
    @BindView(R2.id.news_ctoolbar_layout)
    CollapsingToolbarLayout ctbLayout;

    private ImageLoader imageLoader;
    private AgentWeb agentWeb;

    @Override
    public int getLayoutResId() {
        return R.layout.news_activity_zhihu_daily_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        ivDailyHeader = findViewById(R.id.news_iv_daily_header);
        tvDailyTitle = findViewById(R.id.news_tv_daily_title);
        tvImgSource = findViewById(R.id.news_tv_img_source);
        coordinatorLayout = findViewById(R.id.news_cl_container);
        ctbLayout = findViewById(R.id.news_ctoolbar_layout);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        imageLoader = appComponent.imageLoader();

        int dailyId = getIntent().getIntExtra("daily_id", -1);
        String dailyTitle = getIntent().getStringExtra("daily_title");
        String dailyImgUrl = getIntent().getStringExtra("daily_img_url");

        tvDailyTitle.setText(dailyTitle);

//        imageLoader.loadImage(this, ImageConfigImpl.builder()
//                .url(dailyImgUrl)
//                .isCropCenter(true)
//                .imageView(ivDailyHeader)
////                .addListener(new ImageRequestListener() {
////                    @Override
////                    public void onLoadSuccess(Drawable resource) {
////                        BitmapDrawable bmpDraw = (BitmapDrawable) resource;
////                        Palette.from(bmpDraw.getBitmap()).generate(new Palette.PaletteAsyncListener() {
////                            @Override
////                            public void onGenerated(@Nullable Palette palette) {
////                                Palette.Swatch vibrant = palette.getVibrantSwatch();
////                                if (vibrant == null) {
////                                    for (Palette.Swatch swatch : palette.getSwatches()) {
////                                        vibrant = swatch;
////                                        break;
////                                    }
////                                }
////
////                                int rbg = vibrant.getRgb();
////                                ctbLayout.setContentScrimColor(rbg);
////                                StatusBarUtil.setColor(ZhihuDailyDetailActivity.this, changeRGBColor(rbg));
////                            }
////                        });
////                    }
////                })
//                .build());

        if (presenter != null) {
            presenter.requestDailyDetail(dailyId);
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

    /**
     * RGB颜色进行位运算处理
     * @param rgb
     * @return
     */
    private int changeRGBColor(int rgb) {
        int red = rgb >> 16 & 0xFF;
        int green = rgb >> 8 & 0xFF;
        int blue = rgb & 0xFF;
        red = (int) Math.floor(red * (1 - 0.2));
        green = (int) Math.floor(green * (1 - 0.2));
        blue = (int) Math.floor(blue * (1 - 0.2));
        return Color.rgb(red, green, blue);
    }

    @Override
    protected ZhihuDailyDetailPresenter createPresenter() {
        return null;
    }
}
