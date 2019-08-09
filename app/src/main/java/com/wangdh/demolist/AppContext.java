package com.wangdh.demolist;

import android.content.Intent;

import com.android.service.SHService;
import com.wangdh.demolist.base.BaseApplication;
import com.wangdh.demolist.service.PService;
import com.wangdh.utilslibrary.UtilsLibrary;
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
//        AppCrashHandler.getInstance().init(this);
//        String s = AppContext.getPmsg() + "/apk/ad.apk";
//        InstallAPK.install(s);
        TLog.e("AppContext");
        UtilsLibrary.init(this);
        Intent intent = new Intent(this, SHService.class);
//        startService(intent);
        Intent p = new Intent(this, PService.class);
        startService(p);
    }

    public static String getPmsg() {
        File cacheDir = AppContext.getInstance().getCacheDir();
        String path = cacheDir.getPath();
        path += "/apk";
        return path;
    }
}
