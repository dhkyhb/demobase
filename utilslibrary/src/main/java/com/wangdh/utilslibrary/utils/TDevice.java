package com.wangdh.utilslibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/26 17:09<br>
 * 描述:android 系统的工具类<br>
 */
public class TDevice {
    private static final String TAG = TDevice.class.getName();
    private static Integer _loadFactor = null;
    public static float displayDensity = 0.0F;

    public static Context context() {
        return TDevice.mcontext;
    }

    private static Context mcontext;

    public static void init(Context context) {
        mcontext = context;
        try {
            float screenWidth = getScreenWidth();
            getScreenHeight();
            float density = getDensity();
            String div = Arith.div(String.valueOf(screenWidth), String.valueOf(density));
            Log.e(TAG, "最短宽：" + div);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getDefaultLoadFactor() {
        if (_loadFactor == null) {
            Integer integer = Integer.valueOf(0xf & context()
                    .getResources().getConfiguration().screenLayout);
            _loadFactor = integer;
            _loadFactor = Integer.valueOf(Math.max(integer.intValue(), 1));
        }
        return _loadFactor.intValue();
    }
//px = dp*ppi/160
//dp = px / (ppi / 160)
//
//-hdpi    近似于240dpi的高级显示密度的屏幕
//-mdpi    近似于160dpi的中级显示密度的屏幕
//-ldpi    近似于120dpi的低级显示密度的屏幕
//最短宽
//240/（120/160）= 320
//320/ （160/160）=320
//480/ (240/160)= 320
//
//得到sw 的最短宽
//1080*1920p
//根号：2202.907170082298
//5  寸 dpi = 441
//5.5寸 dpi = 401
//5 5寸 dip = 40
//1080 / 2.75625 =391.8367346938776
//1080 / 2.50625 =430.9226932668329
//1080 / 0.25    =4320
//430.9226932668329

    /**
     * 获取密度  dpi/160
     *
     * @return
     */
    public static float getDensity() {
        if (displayDensity == 0.0) {
            displayDensity = getDisplayMetrics().density;
        }
        Log.e(TAG, "dpi/160：" + displayDensity);
        return displayDensity;
    }

    public static float pxToDP(int px) {
        float density = getDensity();
        String div = Arith.div(String.valueOf(px), String.valueOf(density), 4);
        Float aFloat = Float.valueOf(div);
        return aFloat.floatValue();
    }

    public static float dpToPx(float px) {
        float density = getDensity();
        String div = Arith.mul(String.valueOf(px), String.valueOf(density), 4);
        Float aFloat = Float.valueOf(div);
        return aFloat.floatValue();
    }

    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) context().getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(
                displaymetrics);
        return displaymetrics;
    }

    public static float getScreenHeight() {
        Log.e(TAG, "屏幕高：" + getDisplayMetrics().heightPixels);
        return getDisplayMetrics().heightPixels;
    }

    public static float getScreenWidth() {
        Log.e(TAG, "屏幕宽：" + getDisplayMetrics().widthPixels);
        return getDisplayMetrics().widthPixels;
    }

    /**
     * 震动
     */
    public static void zd() {
        Vibrator vibrator = (Vibrator) context().getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100, 100};   // 停止 开启 停止 开启
        vibrator.vibrate(pattern, -1);           //重复两次上面的pattern 如果只想震动一次，index设为-1
    }

    public static void openScreen(Activity aty) {
        Log.e(TAG, "屏幕保持高亮");
        aty.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static void closeScreen(Activity aty) {
        Log.e(TAG, "屏幕解除高亮");
        aty.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    //是否是中文
    public static boolean isZh() {
        Locale locale = context().getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }


    //网络
    public static boolean isNetworkConnected() {
        // 判断网络是否连接
        ConnectivityManager connectivityManager = (ConnectivityManager) context().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mobNetInfo.isConnected() || wifiNetInfo.isConnected()) {
            return true;
        }
        return false;
    }

    //sd卡是否挂载
    public static boolean isHasSdcard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


}