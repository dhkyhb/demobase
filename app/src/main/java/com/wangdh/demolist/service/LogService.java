package com.wangdh.demolist.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.wangdh.utilslibrary.utils.root.Shell;

public class LogService extends Service {
    public LogService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread() {
            @Override
            public void run() {
                super.run();
                Shell shell = new Shell();
                try {
//                    shell.recordLog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
