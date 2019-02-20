package com.mcgrady.shop.app.mvp.ui.weiget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mcgrady.news.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/2/20
 */

public class ProductSkuPopWin extends BasePopupWindow {

    public ProductSkuPopWin(Context context) {
        super(context);
    }

    @Override
    public View onCreateContentView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.shop_popwin_product_sku, null);
    }
}
