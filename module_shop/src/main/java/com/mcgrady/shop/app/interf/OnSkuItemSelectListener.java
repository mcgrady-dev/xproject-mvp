package com.mcgrady.shop.app.interf;

import com.mcgrady.shop.app.mvp.model.bean.SkuAttribute;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/2/20
 */

public interface OnSkuItemSelectListener {

    void onSelect(int position, boolean select, SkuAttribute attribute);
}
