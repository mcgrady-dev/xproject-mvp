package com.mcgrady.main.di.module;

import com.mcgrady.main.mvp.contract.EyepetizerLoginContract;
import com.mcgrady.main.mvp.model.EyepetizerLoginModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/03/2019 16:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class EyepetizerLoginModule {

    @Binds
    abstract EyepetizerLoginContract.Model bindEyepetizerLoginModel(EyepetizerLoginModel model);
}