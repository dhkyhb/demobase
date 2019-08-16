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

//android:exported	表示是否允许除了当前程序之外的其他程序访问这个服务
//android:enabled	表示是否启用这个服务
//android:permission	是权限声明
//android:process	是否需要在单独的进程中运行,当设置为android:process=”:remote”时，代表Service在单独的进程中运行。注意“：”很重要，它的意思是指要在当前进程名称前面附加上当前的包名，所以“remote”和”:remote”不是同一个意思，前者的进程名称为：remote，而后者的进程名称为：App-packageName:remote。
//android:isolatedProcess 	设置 true 意味着，服务会在一个特殊的进程下运行，这个进程与系统其他进程分开且没有自己的权限。与其通信的唯一途径是通过服务的API(bind and start)。
//
public class StartAppService extends Service {
    public StartAppService() {
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
//                        try {
//                            isStart(StartAppService.this);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
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
        TLog.e("进程开始" + (intent == null));
        if (intent != null) {
            String key = intent.getStringExtra("key");
            if (!TextUtils.isEmpty(key)) {
                TLog.e("监听到了 :" + key);
                startyc();

//                try {
//                    new Shell().writeLog();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
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
//        if (!run) {
            launchAPK1(p);
//        }
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
