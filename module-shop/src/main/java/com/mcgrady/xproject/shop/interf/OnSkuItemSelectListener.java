package com.mcgrady.xproject.shop.interf;

import com.mcgrady.xproject.shop.mvp.model.bean.SkuAttribute;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/2/20
 */

public interface OnSkuItemSelectListener {

    void onSelect(int position, boolean select, SkuAttribute attribute);
}
