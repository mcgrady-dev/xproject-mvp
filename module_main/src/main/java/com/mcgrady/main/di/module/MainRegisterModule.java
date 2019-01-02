package com.mcgrady.main.di.module;


import com.mcgrady.main.mvp.contract.MainRegisterContract;
import com.mcgrady.main.mvp.model.MainRegisterModel;

import dagger.Binds;
import dagger.Module;


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
@Module
public abstract class MainRegisterModule {

    @Binds
    abstract MainRegisterContract.Model bindMainRegisterModel(MainRegisterModel model);
}