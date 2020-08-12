package com.mcgrady.common_service.zhihu.service;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.mcgrady.common_service.zhihu.bean.ZhihuInfo;

/**
 * 向外提供服务的接口实现类, 在此类中实现一些具有特定功能的方法提供给外部, 即可让一个组件与其他组件或宿主进行交互
 *
 * Created by mcgrady on 2018/12/21.
 */

public interface ZhihuInfoService extends IProvider {

    ZhihuInfo getInfo();
}
