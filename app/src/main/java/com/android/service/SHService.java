package com.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.wangdh.utilslibrary.rx.RXTest;
import com.wangdh.utilslibrary.utils.logger.TLog;
import com.wangdh.utilslibrary.utils.root.Shell;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SHService extends Service {
    public SHService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TLog.e("进程创建");
        tt();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TLog.e("进程开始");
        String key = intent.getStringExtra("key");
        if (!TextUtils.isEmpty(key)) {
            TLog.e("监听到了异常 :" + key);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static Disposable mDisposable;

    //                .doOnNext(new Consumer<Long>() {
//        @Override
//        public void accept(@NonNull Long aLong) throws Exception {
//            Log.e(TAG, "accept: doOnNext : "+aLong );
//        }
//    })
    public void tt() {
        mDisposable = Flowable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        Log.e("守护服务", "accept: 设置文本 ：" + aLong);
                    }
                });
    }
}
