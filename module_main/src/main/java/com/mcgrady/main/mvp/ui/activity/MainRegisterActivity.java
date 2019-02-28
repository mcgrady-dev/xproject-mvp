package com.mcgrady.main.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mcgrady.common_core.base.BaseActivity;
import com.mcgrady.common_core.di.component.AppComponent;
import com.mcgrady.main.R;
import com.mcgrady.main.di.component.DaggerMainRegisterComponent;
import com.mcgrady.main.mvp.contract.MainRegisterContract;
import com.mcgrady.main.mvp.presenter.MainRegisterPresenter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/21/2018 14:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainRegisterActivity extends BaseActivity<MainRegisterPresenter> implements MainRegisterContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainRegisterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.main_activity_register;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
