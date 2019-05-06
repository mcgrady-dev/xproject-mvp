package com.mcgrady.main.mvp.ui.fragment.xhs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcgrady.main.R;
import com.mcgrady.main.R2;
import com.mcgrady.main.interf.XhsWelcomeListener;
import com.mcgrady.main.widget.ChildViewPager;
import com.mcgrady.main.widget.WelcomeIndicator;
import com.mcgrady.xskeleton.base.BaseFragment;
import com.mcgrady.xskeleton.di.component.AppComponent;

import butterknife.BindView;

public class XhsWelcomeFragment extends BaseFragment {

    @BindView(R2.id.main_vp_imageanim)
    ChildViewPager vpImageAnim;
    @BindView(R2.id.main_vp_textanim)
    ChildViewPager vpTextAnim;
    @BindView(R2.id.main_view_indicator)
    WelcomeIndicator viewIndicator;
    @BindView(R2.id.main_tv_skip)
    TextView tvSkip;
    @BindView(R2.id.main_rl_indicator)
    RelativeLayout rlIndicator;
    @BindView(R2.id.main_rl_parent)
    RelativeLayout rlParent;


    private int oldPosition = -1;
    private boolean isMoveParent = false;
    private boolean fristPageSuperLock = false;

    private XhsWelcomeListener xhsWelcomeListener;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment_xhs_welcome, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    public TextView getTvSkip() {
        return tvSkip;
    }

    public ChildViewPager getVpImage() {
        return vpImageAnim;
    }

    public ChildViewPager getVpText() {
        return vpTextAnim;
    }

    public int getOldPosition() {
        return oldPosition;
    }

    public boolean isMoveParent() {
        return isMoveParent;
    }

    public boolean isFristPageSuperLock() {
        return fristPageSuperLock;
    }

    public void setXhsWelcomeListener(XhsWelcomeListener xhsWelcomeListener) {
        this.xhsWelcomeListener = xhsWelcomeListener;
    }

    public XhsWelcomeListener getXhsWelcomeListener() {
        return xhsWelcomeListener;
    }
}
