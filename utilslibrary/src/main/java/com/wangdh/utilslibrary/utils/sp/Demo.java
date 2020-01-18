package com.wangdh.utilslibrary.utils.sp;

/**
 * Created by wangdh on 2017/9/28.
 * name：
 * 描述：
 */

public class Demo {
    private static final String TAG = Demo.class.getName() + ".";

    @SP(value = "海波", type = String.class)
    public static final String demo = TAG + "demo";

    @SP(value = "1", type = Integer.class)
    public static final String init = TAG + "init";

    public static void test() {
//        SPFactory.set(MerchantConfig.merchantName, false);
//        SPFactory.set(MerchantConfig.merchantName, 1);
//        SPFactory.set(MerchantConfig.merchantName, getString(R.string.test_merchant));
//        String s = SPFactory.get(MerchantConfig.merchantName, String.class);
//
//        System.out.println("获取到的数据：" + s);
    }
}
