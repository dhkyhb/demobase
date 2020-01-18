package com.wangdh.utilslibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
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


    // 判断网络是否连接
    public static boolean isNetworkConnected(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                //有网处理
                return true;
            } else {
                //无网显示个提示什么的
            }
        } catch (Exception e) {
            //ignore
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


    /**
     * Android 6.0 之前（不包括6.0）获取mac地址
     * 必须的权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     *
     * @param context * @return
     */
    public static String getMacDefault(Context context) {
        TLog.e("小于6.0系统的mac");
        String mac = "02:00:00:00:00:00";
        if (context == null) {
            return mac;
        }

        WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return mac;
        }
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
        }
        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }
        return mac;
    }

    /**
     * Android 6.0-Android 7.0 获取mac地址
     */
    public static String getMacAddress() {
        TLog.e("6.0~7.0系统的mac");

        String WifiAddress = "02:00:00:00:00:00";
        try {
            WifiAddress = new BufferedReader(new FileReader(new File("/sys/class/net/wlan0/address"))).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return WifiAddress;
    }

    /**
     * Android 7.0之后获取Mac地址
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET"></uses-permission>
     * @return
     */
    /**
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET" />
     *
     * @return
     */
    private static String getMacFromHardware() {
        TLog.e("7.0系统的mac");
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    private static String mac = "";

    /**
     * 获取mac地址（适配所有Android版本）
     * 添加<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     * @return
     */
    public static String getMac(Context context) {
        if (!TextUtils.isEmpty(mac)) {
            return mac;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacDefault(context);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = getMacAddress();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mac = getMacFromHardware();
        }
        return mac;
    }

    public static String getSN() {
        String serialNumber = Build.SERIAL;
        return serialNumber;
    }

    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = "";
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }
    /**
     * 获取版本号
     *
     * @param context 上下文
     *
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        //获取包管理器
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //返回版本号
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}