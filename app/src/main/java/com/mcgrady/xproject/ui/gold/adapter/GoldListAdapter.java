package com.mcgrady.xproject.ui.gold.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mcgrady.xproject.R;
import com.mcgrady.xproject.model.entity.GoldListEntity;

import java.util.List;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2017/12/26
 * @des:
 */

public class GoldListAdapter extends BaseMultiItemQuickAdapter<GoldListEntity, BaseViewHolder> {

    public static final int ITEM_TYPE_TITLE = 0;
    public static final int ITEM_TYPE_HOT = 1;
    public static final int ITEM_TYPE_CONTENT = 2;

    private String mType;
    private boolean mHotFlag = true;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GoldListAdapter(List<GoldListEntity> data) {
        super(data);

        addItemType(ITEM_TYPE_TITLE, R.layout.item_gold_hot_header);
        addItemType(ITEM_TYPE_HOT, R.layout.item_gold_hot);
        addItemType(ITEM_TYPE_CONTENT, R.layout.item_gold_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoldListEntity item) {

    }
}
