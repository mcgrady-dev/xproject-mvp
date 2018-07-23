package com.mcgrady.news.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mcgrady.core.Constants;
import com.mcgrady.core.base.BaseFragment;
import com.mcgrady.news.R;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/7/23
 */

@Route(path = Constants.MODULE_NEWS_NAME + "/GoldNewsFragment")
public class GoldNewsFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.news_fragment_gold;
    }

    @Override
    protected void initEventAndData(View view) {

    }

    @Override
    public void initInject(Bundle savedInstanceState) {

    }
}
