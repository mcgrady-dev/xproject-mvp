package com.mcgrady.xproject.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mcgrady.xproject.model.bean.GoldListBean;

import java.util.List;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2017/12/26
 * @des:
 */

public class GoldListEntity implements MultiItemEntity {

    private int itemType;
    private List<GoldListBean> mData;

    private GoldListEntity() {
    }

    public GoldListEntity(int itemType, List<GoldListBean> data) {
        this.itemType = itemType;
        mData = data;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public List<GoldListBean> getData() {
        return mData;
    }
}
