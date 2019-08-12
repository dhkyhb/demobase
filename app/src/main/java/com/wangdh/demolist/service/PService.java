package com.wangdh.demolist.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.wangdh.utilslibrary.utils.logger.TLog;
import com.wangdh.utilslibrary.utils.root.Shell;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 服务被系统销毁后，会自动启动，这个时候intent 是null
 */
public class PService extends Service {
    public PService() {
    }

    private Disposable mDisposable;

    @Override
    public void onCreate() {
        super.onCreate();
        TLog.e("进程创建");
        mDisposable = Flowable.interval(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        Log.e("心跳", "" + aLong);
//                        startyc();
                        try {
                            isStart(PService.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TLog.e("进程开始");
        String key = intent.getStringExtra("key");
        if (!TextUtils.isEmpty(key)) {
            TLog.e("监听到了异常 :" + key);
            try {
                new Shell().writeLog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void startyc() {
        String p = "com.sand.airdroid";
        boolean run = new Shell().isRun(p);
        TLog.e("com.sand.airdroid 是否运行：" + run);
        if (!run) {
            launchAPK1(p);
        }
    }

    public boolean isStart(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = manager.getRunningAppProcesses();
//        for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
//
//            TLog.e("进程:" + runningAppProcess.processName+" "+runningAppProcess.pid);
//        }
        List<ActivityManager.RunningTaskInfo> task = manager.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : task) {
            TLog.e("1:" + info.topActivity.getPackageName() + "--" + info.baseActivity.getPackageName());
        }
        // Get the info we need for comparison.
        ComponentName componentInfo = task.get(0).topActivity;
        // Check if it matches our package name.
//        TLog.e("1:" + componentInfo.getPackageName());
//        TLog.e("2:" + this.getPackageName());
        if (!componentInfo.getPackageName().equals(this.getPackageName())) {
            //android 5.0 开始 需要系统权限
            manager.killBackgroundProcesses(componentInfo.getPackageName());
            return false;
        }
        return true;
    }

    // 判断应用是否在运行
    public boolean isRun(Context context, String mPackageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        //100表示取的最大的任务数，info.topActivity表示当前正在运行的Activity，info.baseActivity表示系统后台有此进程在运行
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(mPackageName) || info.baseActivity.getPackageName().equals(mPackageName)) {
                isAppRunning = true;
                Log.i("ActivityService", info.topActivity.getPackageName() + " info.baseActivity.getPackageName()=" + info.baseActivity.getPackageName());
                break;
            }
        }
        if (isAppRunning) {
            Log.i("ActivityService", "该程序正在运行");
        } else {
            Log.i("ActivityService", "该程序没有运行");
        }
        return isAppRunning;
    }

    public void launchAPK1(String pacg) {
        PackageManager packageManager = this.getPackageManager();
        Intent it = packageManager.getLaunchIntentForPackage(pacg);
        this.startActivity(it);
    }

    /**
     * 判断程序是否在后台运行
     */
    public static boolean isRunBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    // 表明程序在后台运行
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 判断程序是否在前台运行（当前运行的程序）
     */
    public boolean isRunForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;// 程序运行在前台
            }
        }
        return false;
    }
}
