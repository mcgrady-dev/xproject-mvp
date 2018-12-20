package com.mcgrady.core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>List集合工具</p>
 *
 * @author: mcgrady
 * @date: 2018/11/26
 */

public class ArraysUtils {

    /**
     * 拆分集合
     *
     * @param resList 要拆分的集合
     * @param count 每个集合的元素个数
     * @param <T>
     * @return 返回拆分后的各个集合
     */
    public static  <T> List<List<T>> split(List<T> resList, int count){

        if (resList == null || count < 1) {
            return null;
        }
        List<List<T>> ret=new ArrayList<List<T>>();
        int size=resList.size();
        //数据量不足count指定的大小
        if (size <= count) {
            ret.add(resList);
        } else {
            int pre = size / count;
            int last = size % count;
            //前面pre个集合，每个大小都是count个元素
            for (int i = 0; i < pre; i++) {
                List<T> itemList = new ArrayList<T>();
                for (int j = 0; j < count; j++) {
                    itemList.add(resList.get(i * count + j));
                }
                ret.add(itemList);
            }
            //last的进行处理
            if (last > 0) {
                List<T> itemList = new ArrayList<T>();
                for (int i = 0; i < last; i++) {
                    itemList.add(resList.get(pre * count + i));
                }
                ret.add(itemList);
            }
        }
        return ret;
    }
}
