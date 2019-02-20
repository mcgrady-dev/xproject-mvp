package com.mcgrady.shop.app.mvp.ui.weiget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.internal.FlowLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.mcgrady.common_res.utils.ViewUtils;
import com.mcgrady.shop.app.mvp.model.bean.SkuAttribute;

import java.util.List;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/2/20
 */

public class SkuItemLayout extends LinearLayout {

    private TextView attributeNameTv;
    private SkuFlowLayout attributeValueLayout;
    private OnSkuItemSelectListener listener;

    public SkuItemLayout(Context context) {
        super(context);
        init(context);
    }

    public SkuItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SkuItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);

        attributeNameTv = new TextView(context);
        attributeNameTv.setId(ViewUtils.generateViewId());
        attributeNameTv.setTextColor(Color.parseColor("#333333"));
        attributeNameTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        attributeNameTv.setIncludeFontPadding(false);
        LayoutParams attributeNameParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        attributeNameParams.leftMargin = ConvertUtils.dp2px(15);
        attributeNameParams.topMargin = ConvertUtils.dp2px(15);
        attributeNameTv.setLayoutParams(attributeNameParams);
        addView(attributeNameTv);

        attributeValueLayout = new SkuFlowLayout(context);
        attributeValueLayout.setId(ViewUtils.generateViewId());
        attributeValueLayout.setMinimumHeight(ConvertUtils.dp2px(25));
        attributeValueLayout.setChildSpacing(ConvertUtils.dp2px(15));
        attributeValueLayout.setRowSpacing(ConvertUtils.dp2px(15));
        LayoutParams attributeValueParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        attributeValueParams.leftMargin = ConvertUtils.dp2px(15);
        attributeValueParams.rightMargin = ConvertUtils.dp2px(15);
        attributeValueParams.topMargin = ConvertUtils.dp2px(15);
        attributeValueParams.bottomMargin = ConvertUtils.dp2px(10);
        attributeValueLayout.setLayoutParams(attributeValueParams);
        addView(attributeValueLayout);

        View line = new View(context);
        line.setBackgroundResource(Color.parseColor("#dddddd"));
        LayoutParams lineParams = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
        lineParams.leftMargin = ConvertUtils.dp2px(15);
        lineParams.rightMargin = ConvertUtils.dp2px(15);
        lineParams.topMargin = ConvertUtils.dp2px(5);
        line.setLayoutParams(lineParams);
        addView(line);
    }

    public void setListener(OnSkuItemSelectListener listener) {
        this.listener = listener;
    }

    public void buildItemLayout(int position, String attributeName, List<String> attributeValueList) {
        attributeNameTv.setText(attributeName);
        attributeValueLayout.removeAllViewsInLayout();
        for (int i = 0; i < attributeValueList.size(); i++) {
            SkuItemView itemView = new SkuItemView(getContext());
            itemView.setId(ViewUtils.generateViewId());
            itemView.setAttributeValue(attributeValueList.get(i));
            itemView.setOnClickListener(new ItemClickListener(position, itemView));
            itemView.setLayoutParams(new FlowLayout.LayoutParams(
                    FlowLayout.LayoutParams.WRAP_CONTENT,
                    ConvertUtils.dp2px(25)));
            attributeValueLayout.addView(itemView);
        }
    }

    /**
     * 清空是否可点击，选中状态
     */
    public void clearItemViewStatus() {
        for (int i = 0; i < attributeValueLayout.getChildCount(); i++) {
            SkuItemView itemView = (SkuItemView) attributeValueLayout.getChildAt(i);
            itemView.setSelected(false);
            itemView.setEnabled(false);
        }
    }

    /**
     * 设置指定属性为可点击状态
     *
     * @param attributeValue
     */
    public void optionItemViewEnableStatus(String attributeValue) {
        for (int i = 0; i < attributeValueLayout.getChildCount(); i++) {
            SkuItemView itemView = (SkuItemView) attributeValueLayout.getChildAt(i);
            if (attributeValue.equals(itemView.getAttributeValue())) {
                itemView.setEnabled(true);
            }
        }
    }

    /**
     * 设置指定属性为选中状态
     *
     * @param selectValue
     */
    public void optionItemViewSelectStatus(SkuAttribute selectValue) {
        for (int i = 0; i < attributeValueLayout.getChildCount(); i++) {
            SkuItemView itemView = (SkuItemView) attributeValueLayout.getChildAt(i);
            if (selectValue.getValue().equals(itemView.getAttributeValue())) {
                itemView.setEnabled(true);
                itemView.setSelected(true);
            }
        }
    }

    /**
     * 当前属性集合是否有选中项
     * @return
     */
    public boolean isSelected() {
        for (int i = 0; i < attributeValueLayout.getChildCount(); i++) {
            SkuItemView itemView = (SkuItemView) attributeValueLayout.getChildAt(i);
            if (itemView.isSelected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取属性名称
     * @return
     */
    public String getAttributeName() {
        return attributeNameTv.getText().toString();
    }

    private void onSkuItemClicked(int position, SkuItemView view) {
        boolean selected = !view.isSelected();
        SkuAttribute attribute = new SkuAttribute();
        attribute.setKey(attributeNameTv.getText().toString());
        attribute.setValue(view.getAttributeValue());
        listener.onSelect(position, selected, attribute);
    }

    private class ItemClickListener implements OnClickListener {
        private int position;
        private SkuItemView view;

        ItemClickListener(int position, SkuItemView view) {
            this.position = position;
            this.view = view;
        }

        @Override
        public void onClick(View v) {
            onSkuItemClicked(position, view);
        }
    }

    interface OnSkuItemSelectListener {
        void onSelect(int position, boolean select, SkuAttribute attribute);
    }
}
