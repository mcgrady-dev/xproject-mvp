package com.mcgrady.shop.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.mcgrady.common_core.widget.NoScrollViewPager;
import com.mcgrady.shop.R;
import com.mcgrady.shop.R2;
import com.mcgrady.shop.mvp.contract.ProductDetailsContract;
import com.mcgrady.shop.mvp.presenter.ProductDetailsPresenter;
import com.mcgrady.shop.mvp.ui.weiget.ProductSkuPopup;
import com.mcgrady.common_core.base.BaseActivity;
import com.mcgrady.xtitlebar.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/2/20
 */

public class ProductDetailsActivity extends BaseActivity<ProductDetailsPresenter> implements ProductDetailsContract.View {

    @BindView(R2.id.shop_tb_product_detail)
    TitleBar titlbar;
    @BindView(R2.id.shop_nsvp_product_detail)
    NoScrollViewPager viewPager;
    @BindView(R2.id.shop_tv_add_cart)
    TextView tvAddCart;
    @BindView(R2.id.shop_tv_pay)
    TextView tvPay;

    @Override
    public int getLayoutResId() {
        return R.layout.shop_activity_product_detail;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick(R2.id.shop_tv_add_cart)
    void OnClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.shop_tv_add_cart) {
            new ProductSkuPopup(ProductDetailsActivity.this).showPopupWindow();
        }
    }

    @Override
    protected ProductDetailsPresenter createPresenter() {
        return null;
    }
}
