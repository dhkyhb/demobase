package com.wangdh.utilslibrary.utils.file;

import android.content.Context;

/**
 * @author wangdh
 * @time 2019/8/5 17:40
 * @describe
 */
public class FileToolTest {
    //测试拷贝文件
    public static void test(Context context) {
        new FileAssetsTool().copyAssetsToSD(context, "srca.cer", FileSDTool.getSDPath() + "AA/", "srca.cer", null);
    }
}
