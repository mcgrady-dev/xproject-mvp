package com.mcgrady.uikit.dialog;

import java.io.Serializable;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2018/4/23
 * @des:
 */
public class BuildParams implements Serializable {
    public int mIconId = 0;

    public int themeResId;

    public CharSequence title;

    public CharSequence message;

    public CharSequence positiveText;

    public CharSequence neutralText;

    public CharSequence negativeText;

    public CharSequence[] items;

    public boolean[] checkedItems;

    public boolean isMultiChoice;

    public boolean isSingleChoice;

    public int checkedItem;
}
