package com.mcgrady.common_res.utils;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/5/7
 * @des: EditText最大输入观察器
 */
public class MaxInputTextWatcher implements TextWatcher {

    private EditText editText = null;
    private int maxLength = 0;
    private TextWatcher textWatcherExternal;

    /**
     *
     * @param editText
     * @param maxLength
     * @param listener 外部观察器
     */
    public MaxInputTextWatcher(EditText editText, int maxLength, TextWatcher listener) {
        this.editText = editText;
        this.maxLength = maxLength;
        this.textWatcherExternal = listener;
    }

    public MaxInputTextWatcher(EditText editText, int maxLength) {
        this(editText, maxLength, null);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (textWatcherExternal != null) {
            textWatcherExternal.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Editable editable = editText.getText();
        //原字符串长度
        int length = editable.length();
        //如果原字符串长度大于最大长度
        if (length > maxLength) {
            //getSelectionEnd：获取光标结束的索引值
            int selectEndIndex = Selection.getSelectionEnd(editable);
            //旧字符串
            String str = editable.toString();
            //截取新字符串
            String newStr = str.substring(0, maxLength);
            editText.setText(newStr);
            editable = editText.getText();
            //新字符串长度
            int newLength = editable.length();
            //如果光标结束的索引值超过新字符串长度
            if (selectEndIndex > newLength) {
                selectEndIndex = editable.length();
                ToastUtils.showShort("最多只能输入" + selectEndIndex + "个字");
            }
            //设置新光标所在的位置
            Selection.setSelection(editable, selectEndIndex);
        }

        if (textWatcherExternal != null) {
            if (length <= maxLength) {
                textWatcherExternal.onTextChanged(s, start, before, count);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (textWatcherExternal != null) {
            textWatcherExternal.afterTextChanged(s);
        }
    }
}
