package com.wangdh.demolist;

import com.wangdh.dblibrary.DBLibrary;
import com.wangdh.demolist.base.BaseApplication;
import com.wangdh.utilslibrary.utils.logger.TLog;

import java.io.File;

/**
 * Created by wangdh on 2016/11/16.
 * name：
 * 描述：
 */

public class AppContext extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCrashHandler.getInstance().init(this);
//        String s = AppContext.getPmsg() + "/apk/ad.apk";
//        InstallAPK.install(s);
        TLog.e("AppContext");
        DBLibrary.init(this);

    }

    public static String getPmsg() {
        File cacheDir = AppContext.getInstance().getCacheDir();
        String path = cacheDir.getPath();
        path += "/apk";
        return path;
    }
}
