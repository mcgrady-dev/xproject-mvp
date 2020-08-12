package com.mcgrady.module_test.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mcgrady on 2020/8/8.
 */
public class TestModel {

    public <T extends Object> List<Map<String, T>> getSimpleListData(int len) {
        String[] names = new String[]{"B神", "基神", "曹神"};
        String[] says = new String[]{"无形被黑，最为致命", "大神好厉害~", "我将带头日狗~"};

        List<Map<String, T>> listitem = new ArrayList<Map<String, T>>();
        for (int j = 0; j <= len; j++) {
            for (int i = 0; i < names.length; i++) {
                Map<String, T> showitem = new HashMap<String, T>();
                showitem.put("name", (T) (names[i] + String.valueOf(j) + String.valueOf(i)));
                showitem.put("says", (T) (says[i] + String.valueOf(j) + String.valueOf(i)));
                listitem.add(showitem);
            }
        }

        return listitem;
    }
}
