package com.mcgrady.news.mvp.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/2/26
 */

public class ZhihuDailyMultipleItem<T> implements MultiItemEntity {

    private int itemType;
    private T data;

    private ZhihuDailyMultipleItem() {
    }

    public ZhihuDailyMultipleItem(int itemType, T data) {
        this.itemType = itemType;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
