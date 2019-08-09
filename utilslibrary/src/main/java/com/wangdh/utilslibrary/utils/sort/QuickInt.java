package com.wangdh.utilslibrary.utils.sort;

/**
 * Created by wangdh on 2018/5/29.
 */

public class QuickInt extends QuickComparable<Integer> {

    @Override
    public int compareTo(Integer o) {
        return o.intValue();
    }
}
