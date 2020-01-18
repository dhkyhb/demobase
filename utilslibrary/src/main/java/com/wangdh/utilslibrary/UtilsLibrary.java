package com.wangdh.utilslibrary;

import android.content.Context;

import com.wangdh.utilslibrary.config.AppConfig;
import com.wangdh.utilslibrary.dblibrary.DBLibrary;
import com.wangdh.utilslibrary.netlibrary.NetLibrary;
import com.wangdh.utilslibrary.utils.MultimediaUtils;
import com.wangdh.utilslibrary.utils.SPTools;
import com.wangdh.utilslibrary.utils.TDevice;
import com.wangdh.utilslibrary.utils.TLog;
import com.wangdh.utilslibrary.utils.sp.SharedPreferencesTools;

/**
 * @author wangdh
 * @time 2019/2/19 14:44
 * @describe UtilsLibrary依赖包管理类
 */
public class UtilsLibrary {

    public static void init(Context context) {
        AppConfig.init(context);
        TLog.init(context);

        TDevice.init(context);
        SPTools.init(context);
        SharedPreferencesTools.init(context);
        DBLibrary.init(context);
        NetLibrary.init();
        MultimediaUtils.init(context);
    }
}
