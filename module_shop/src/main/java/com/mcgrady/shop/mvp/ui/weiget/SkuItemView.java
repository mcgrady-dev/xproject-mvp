package com.mcgrady.shop.mvp.ui.weiget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import com.blankj.utilcode.util.ConvertUtils;
import com.mcgrady.news.R;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/2/20
 */

public class SkuItemView extends android.support.v7.widget.AppCompatTextView {

    private String attributeValue;

    public SkuItemView(Context context) {
        super(context);
        init(context);
    }

    public SkuItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SkuItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setTextColor(ContextCompat.getColorStateList(context, R.drawable.shop_selector_sku_item_text));
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11);
        setSingleLine();
        setGravity(Gravity.CENTER);
        setPadding(ConvertUtils.dp2px(10), 0, ConvertUtils.dp2px(10), 0);

        setMinWidth(ConvertUtils.dp2px(45));
        setMaxWidth(ConvertUtils.dp2px(200));
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        setText(attributeValue);
    }
}
