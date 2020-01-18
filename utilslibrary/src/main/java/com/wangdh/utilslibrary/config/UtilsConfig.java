package com.wangdh.utilslibrary.config;


import com.wangdh.utilslibrary.utils.sp.SP;

/**
 * Created by wangdh on 2018/1/16.
 * name：禁止混淆
 * 描述：
 */

public class UtilsConfig {
    private static final String TAG = UtilsConfig.class.getName() + ".";

    @SP(value = "", type = String.class)
    public static final String appId = TAG + "appId";

    @SP(value = "", type = String.class)
    public static final String activationCode = TAG + "activationCode";

}
