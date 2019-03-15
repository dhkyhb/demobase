package com.wangdh.utilslibrary;

import android.content.Context;

import com.wangdh.utilslibrary.utils.SPTools;
import com.wangdh.utilslibrary.utils.TDevice;
import com.wangdh.utilslibrary.utils.logger.TLog;
import com.wangdh.utilslibrary.utils.sp.SPBaseTools;

/**
 * @author wangdh
 * @time 2019/2/19 14:44
 * @describe UtilsLibrary依赖包管理类
 */
public class UtilsLibrary {

    public static void init(Context context) {
        TDevice.init(context);
        SPTools.init(context);
        SPBaseTools.init(context);
        TLog.init(context);
    }
}
