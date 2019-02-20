package com.mcgrady.shop.app.interf;

import com.mcgrady.shop.app.mvp.model.bean.SkuAttribute;
import com.mcgrady.shop.app.mvp.model.bean.SkuBean;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/2/20
 */

public interface OnSkuSelectListener {

    /**
     * 属性取消选中
     *
     * @param unselectedAttribute
     */
    void onUnselected(SkuAttribute unselectedAttribute);

    /**
     * 属性选中
     *
     * @param selectAttribute
     */
    void onSelect(SkuAttribute selectAttribute);

    /**
     * sku选中
     *
     * @param sku
     */
    void onSkuSelected(SkuBean sku);
}
