package com.wangdh.demolist;

import android.content.Intent;

import com.wangdh.demolist.base.BaseApplication;
import com.wangdh.demolist.service.LogService;
import com.wangdh.demolist.service.StartAppService;
import com.wangdh.utilslibrary.UtilsLibrary;
import com.wangdh.utilslibrary.utils.logger.TLog;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

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
//        new Shell().pr(this);
        JPushInterface.setDebugMode(true); //正式环境时去掉此行代码
        JPushInterface.init(this);

        UtilsLibrary.init(this);
//        Intent p = new Intent(this, StartAppService.class);
//        startService(p);
//        startService(new Intent(this, LogService.class));
    }

    public static String getPmsg() {
        File cacheDir = AppContext.getInstance().getCacheDir();
        String path = cacheDir.getPath();
        path += "/apk";
        return path;
    }
}
