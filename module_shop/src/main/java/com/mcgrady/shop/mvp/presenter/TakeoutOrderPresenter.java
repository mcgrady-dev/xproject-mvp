package com.mcgrady.shop.mvp.presenter;

import android.support.annotation.NonNull;

import com.mcgrady.shop.mvp.contract.TakeoutOrderContract;
import com.mcgrady.xskeleton.mvp.BasePresenter;

/**
 * Created by mcgrady on 2019/5/14.
 */
public class TakeoutOrderPresenter extends BasePresenter<TakeoutOrderContract.Model, TakeoutOrderContract.View> {

    public TakeoutOrderPresenter(@NonNull TakeoutOrderContract.Model model, @NonNull TakeoutOrderContract.View view) {
        super(model, view);
    }
}
