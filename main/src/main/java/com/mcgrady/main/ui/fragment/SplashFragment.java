package com.mcgrady.main.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.mcgrady.core.Constants;
import com.mcgrady.core.base.BaseFragment;
import com.mcgrady.main.R;
import com.mcgrady.main.ui.adapter.SplashViewPagerAdapter;

import butterknife.BindView;

/**
 * <p>引导页</p>
 *
 * @author: mcgrady
 * @date: 2018/5/16
 */
@Route(path = "/main/SplashFragment")
public class SplashFragment extends BaseFragment {

    private int[] guides = {R.mipmap.lichun_1, R.mipmap.jingze_2, R.mipmap.chunfen_3, R.mipmap.lixia_4,
            R.mipmap.xiazhi_5, R.mipmap.liqiu_6, R.mipmap.qiufen_7, R.mipmap.lidong_8, R.mipmap.dongzhi_9};

    private boolean isShowGuide;

    @BindView(R.id.main_splash_view_pager)
    private ViewPager viewPager;

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
    protected void initEventAndData() {

        String version = SPUtils.getInstance(Constants.SP_FILE_NAME_VERSION).getString(Constants.VERSION);
        isShowGuide = version.equals(AppUtils.getAppVersionName());

        // set indicator visible or gone
        if (isShowGuide) {

            viewPager.setAdapter(new SplashViewPagerAdapter(guides));
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == guides.length - 1) {
                        // gone indicator

                    } else {
                        // visible indicator

                    }

                    // set indicator
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        } else {

        }
    }

    @Override
    public void initInject(Bundle savedInstanceState) {

    }
}
