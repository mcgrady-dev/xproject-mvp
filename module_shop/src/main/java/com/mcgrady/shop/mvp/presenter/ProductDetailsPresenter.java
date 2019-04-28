package com.mcgrady.shop.mvp.presenter;

import android.support.annotation.NonNull;

import com.mcgrady.shop.mvp.contract.ProductDetailsContract;
import com.mcgrady.xskeleton.mvp.BasePresenter;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/2/20
 */

public class ProductDetailsPresenter extends BasePresenter<ProductDetailsContract.Model, ProductDetailsContract.View> {


    public ProductDetailsPresenter(@NonNull ProductDetailsContract.Model model, @NonNull ProductDetailsContract.View view) {
        super(model, view);
    }
}
