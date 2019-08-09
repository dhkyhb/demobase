package com.wangdh.demolist;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import com.wangdh.demolist.service.PService;
import com.wangdh.utilslibrary.utils.root.Shell;

/**
 * Created by JuQiu
 * on 2016/9/13.
 * 全局异常捕获 在 application中初始化
 */

public class AppCrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static AppCrashHandler INSTANCE = new AppCrashHandler();
    private Context mContext;

    private AppCrashHandler() {
    }

    public static AppCrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        ex.printStackTrace();
//        Intent intent = new Intent(mContext, PService.class);
//        intent.putExtra("key","error");
//        mContext.startService(intent);
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    new Shell().writeLog();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Looper.prepare();
//                Toast.makeText(mContext, "Demo出现未知异常,即将退出.", Toast.LENGTH_LONG).show();
//                Looper.loop();
//
//            }
//        }.start();
//        //加入延迟 阻止进程马上结束，提供记录日志的线程时间
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return true;
    }
}
