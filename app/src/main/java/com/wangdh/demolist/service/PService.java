package com.wangdh.demolist.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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

public class PService extends Service {
    public PService() {
    }

    private Disposable mDisposable;
    @Override
    public void onCreate() {
        super.onCreate();
        TLog.e("进程创建");
        mDisposable = Flowable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        Log.e("心跳", "" + aLong);
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

    public boolean isStart(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> task = manager.getRunningTasks(1);
//        int size = task.size();
//        for (int i = 0; i < size; i++) {
//            ActivityManager.RunningTaskInfo info = task.get(i);
//            int numActivities = info.numActivities;
//            TLog.e("activity:"+numActivities +" 个");
//            TLog.e("topactivity:"+info.topActivity.getPackageName());
//            TLog.e("topactivity:"+info.topActivity.getClassName());
//            TLog.e("baseActivity:"+info.baseActivity.getPackageName());
//            TLog.e("baseActivity:"+info.baseActivity.getClassName());
//
//        }
        // Get the info we need for comparison.
        ComponentName componentInfo = task.get(0).topActivity;
        // Check if it matches our package name.
        TLog.e("1:" + componentInfo.getPackageName());
        TLog.e("2:" + this.getPackageName());
        if (!componentInfo.getPackageName().equals(this.getPackageName())) {
            //android 5.0 开始 需要系统权限
            manager.killBackgroundProcesses(componentInfo.getPackageName());
            return false;
        }
        return true;
    }
}
