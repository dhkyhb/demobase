package com.wangdh.demolist;

import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.wangdh.dblibrary.DBLibrary;
import com.wangdh.demolist.base.BaseApplication;
import com.wangdh.utilslibrary.utils.logger.TLog;
import com.xuhao.android.libsocket.sdk.OkSocket;

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
        OkSocket.initialize(this);
        AppCrashHandler.getInstance().init(this);
//        String s = AppContext.getPmsg() + "/apk/ad.apk";
//        InstallAPK.install(s);
        TLog.e("AppContext");
        DBLibrary.init(this);
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。

            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("@@", "加载内核是否成功:" + b);
            }
        });

    }

    public static String getPmsg() {
        File cacheDir = AppContext.getInstance().getCacheDir();
        String path = cacheDir.getPath();
        path += "/apk";
        return path;
    }
}
