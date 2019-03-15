package com.wangdh.utilslibrary.utils.sp.demo;


import com.wangdh.utilslibrary.utils.sp.SP;

/**
 * Created by wangdh on 2018/1/16.
 * name：禁止混淆
 * 描述：
 */

public class DemoConfig {
    private static final String TAG = DemoConfig.class.getName() + ".";

    @SP(value = "", type = String.class)
    public static final String demo = TAG + "demo";
}
