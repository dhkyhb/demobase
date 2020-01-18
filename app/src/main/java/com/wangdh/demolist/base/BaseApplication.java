package com.wangdh.demolist.base;

import android.app.Application;
import android.util.Log;

/**
 * @author  wangdh
 * @date 2020/1/18 12:54
 * 描述:
 */
public class BaseApplication extends Application {
    private String TAG = BaseApplication.class.getName();
    private static Application context;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "BaseApplication");
        context = this;
    }

    public static Application getContext() {
        return context;
    }

    public static Application getInstance() {
        return context;
    }
}
