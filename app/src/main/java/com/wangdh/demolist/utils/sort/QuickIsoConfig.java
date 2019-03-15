package com.wangdh.demolist.utils.sort;

import com.wangdh.demolist.pos.iso.ISO8583Config;

/**
 * Created by wangdh on 2018/5/29.
 */

public class QuickIsoConfig extends QuickComparable<ISO8583Config> {

    @Override
    protected int compareTo(ISO8583Config o) {
        return o.id();
    }
}
