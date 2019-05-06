package com.mcgrady.main.mvp.ui.fragment.xhs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcgrady.main.R;
import com.mcgrady.xskeleton.base.BaseFragment;
import com.mcgrady.xskeleton.di.component.AppComponent;

import java.util.ArrayList;

public class XhsLoginFragment extends BaseFragment {


//    @BindView(R2.id.main_sliding_tab_layout)
//    private SlidingTabLayout tabLayout;
//    @BindView(R2.id.main_view_pager_fragment)
//    private ViewPager viewPager;
//    @BindView(R2.id.main_ll_parent)
//    private LinearLayout llParent;


    private String[] titleList;
    private ArrayList<Fragment> fragmentList;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment_xhs_page_login, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

//        titleList = new String[]{"登录", "注册"};
//        fragmentList = new ArrayList<>();
//        fragmentList.add(new XhsLoginPageFragment());
//        fragmentList.add(new XhsRegisterPageFragment());
//
//        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
//            @Override
//            public Fragment getItem(int i) {
//                return fragmentList.get(i);
//            }
//
//            @Override
//            public int getCount() {
//                return fragmentList.size();
//            }
//
//            @Nullable
//            @Override
//            public CharSequence getPageTitle(int position) {
//                return titleList[position];
//            }
//        });
//
//        tabLayout.setViewPager(viewPager, titleList);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    public void playInAnim() {

//        AnimatorSet mAnimatorSet;
//        ObjectAnimator anim3 = ObjectAnimator.ofFloat(llParent, "y", ScreenUtils.getScreenHeight(),
//                ConvertUtils.px2dp(160));
//
//        mAnimatorSet = new AnimatorSet();
//        mAnimatorSet.play(anim3);
//        mAnimatorSet.setDuration(1000);
//        mAnimatorSet.start();
    }
}
