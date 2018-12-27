package com.mcgrady.main.mvp.ui.fragment.xhs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.mcgrady.common_res.widget.SlidingTabLayout;
import com.mcgrady.main.R;
import com.mcgrady.main.R2;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/21/2018 14:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class XhsLoginFragment extends BaseFragment {


    @BindView(R2.id.main_sliding_tab_layout)
    private SlidingTabLayout tabLayout;
    @BindView(R2.id.main_view_pager_fragment)
    private ViewPager viewPager;

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

        titleList = new String[]{"登录", "注册"};
        fragmentList = new ArrayList<>();
        fragmentList.add(new XhsLoginPageFragment());
        fragmentList.add(new XhsRegisterPageFragment());

        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titleList[position];
            }
        });

        tabLayout.setViewPager(viewPager, titleList);
    }

    @Override
    public void setData(@Nullable Object data) {

    }
}
