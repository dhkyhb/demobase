package com.wangdh.demolist.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.wangdh.utilslibrary.rx.RXTest;
import com.wangdh.utilslibrary.utils.logger.TLog;

public class PService extends Service {
    public PService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TLog.e("进程创建");
        RXTest.tt();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TLog.e("进程开始");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
