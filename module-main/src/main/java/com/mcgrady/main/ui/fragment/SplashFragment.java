package com.mcgrady.main.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.rxbus.RxBus;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.mcgrady.core.Constants;
import com.mcgrady.core.base.BaseFragment;
import com.mcgrady.main.R;
import com.mcgrady.main.di.MainDiHelper;
import com.mcgrady.main.event.LoadMainBottomNavigationEvent;
import com.mcgrady.main.persenter.MainPersenter;
import com.mcgrady.main.persenter.contract.MainContract;
import com.mcgrady.main.ui.adapter.SplashViewPagerAdapter;
import com.rd.PageIndicatorView;

/**
 * <p>引导页</p>
 *
 * @author: mcgrady
 * @date: 2018/5/16
 */
@Route(path = "/main/SplashFragment")
public class SplashFragment extends BaseFragment<MainPersenter> implements MainContract.IView {

    private int[] guides = {R.mipmap.lichun_1, R.mipmap.jingze_2, R.mipmap.chunfen_3, R.mipmap.lixia_4,
            R.mipmap.xiazhi_5, R.mipmap.liqiu_6, R.mipmap.qiufen_7, R.mipmap.lidong_8, R.mipmap.dongzhi_9};

    private boolean isShowGuide;

    private ViewPager viewPager;
    private PageIndicatorView indicatorView;
    private TextView tvJoin;

    View fakeStatusBar;

    public static SplashFragment newInstance() {
        Bundle args = new Bundle();
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_splash;
    }

    @Override
    protected void initEventAndData(View view) {
        viewPager = $(R.id.main_splash_view_pager);
        indicatorView = $(R.id.main_splash_indicator_view);
        tvJoin = $(R.id.main_splash_tv_join);
        fakeStatusBar = $(R.id.fake_status_bar);

        BarUtils.setStatusBarAlpha(fakeStatusBar, 0);

        String version = SPUtils.getInstance(Constants.SP_FILE_NAME_VERSION).getString(Constants.VERSION);
        isShowGuide = version.equals(AppUtils.getAppVersionName());

        // set indicator visible or gone
        if (!isShowGuide) {

            // visible root layout

            viewPager.setAdapter(new SplashViewPagerAdapter(guides));
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == guides.length - 1) {
                        // gone indicator
                        indicatorView.setVisibility(View.GONE);
                        tvJoin.setVisibility(View.VISIBLE);
                    } else {
                        // visible indicator
                        indicatorView.setVisibility(View.VISIBLE);
                        tvJoin.setVisibility(View.INVISIBLE);
                    }

                    // set indicator
                    indicatorView.setSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        } else {
            // todo: gone root layout
        }

        tvJoin.setOnClickListener(view1 -> {
            RxBus.getDefault().post(new LoadMainBottomNavigationEvent());
        });


//        @SuppressLint("MissingPermission")
//        String deviceId = PhoneUtils.getDeviceId();
//        mHttpHelper.createApi(ISplashApi.class).getOwspacePicList("android", "1.3.0",
//                TimeUtils.getNowMills(), deviceId)
//                .compose(RxUtil.rxSchedulerHelper())
//                .compose(bindLifecycle())
//                .subscribe(new SplashSubscriber<>(new BaseSubscriberCallback() {
//                    @Override
//                    public void onSuccess(Object response) {
//                        LogUtils.d(((SplashPicBean)response).toString());
//                    }
//
//                    @Override
//                    public void onFail(HttpErrorException error) {
//
//                    }
//
//                    @Override
//                    public void checkReLogin(String errorCode, String errorMsg) {
//
//                    }
//                }));

    }

    @Override
    public void initInject(Bundle savedInstanceState) {
        MainDiHelper.getFragmentComponent(getFragmentModule()).inject(this);
    }
}
