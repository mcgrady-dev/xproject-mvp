package com.mcgrady.zhihu.service;

import android.content.Context;

import com.blankj.utilcode.util.StringUtils;
import com.mcgrady.common_service.zhihu.bean.ZhihuInfo;
import com.mcgrady.common_service.zhihu.service.ZhihuInfoService;
import com.mcgrady.zhihu.R;

/**
 * Created by mcgrady on 2020/8/12.
 */
//@Route(path = RouterHub.ZHIHU_ACTIVITY_DAILY_DETAIL, name = "知乎信息服务")
public class ZhihuInfoServiceImpl implements ZhihuInfoService {

    @Override
    public ZhihuInfo getInfo() {
        return new ZhihuInfo(StringUtils.getString(R.string.app_name));
    }

    @Override
    public void init(Context context) {

    }
}
