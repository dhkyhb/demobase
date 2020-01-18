package com.wangdh.utilslibrary.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangdh
 * @time 2019/7/16 14:12
 * @describe  复制到acitivity下使用
 */
public class PermissionUtils {
    public static void request(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission(activity);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private static void requestPermission(Activity activity) {
        List<String> p = new ArrayList<>();
        for (String permission : PermissionUtils.PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                p.add(permission);
            }
        }
        if (p.size() > 0) {
            activity.requestPermissions(p.toArray(new String[p.size()]), PermissionUtils.PERMISSION_CODES);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void requestPermission(Activity activity, String[] strings) {
        List<String> p = new ArrayList<>();
        for (String permission : strings) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                p.add(permission);
            }
        }
        if (p.size() > 0) {
            activity.requestPermissions(p.toArray(new String[p.size()]), PermissionUtils.PERMISSION_CODES);
        }
    }

    public static final int PERMISSION_CODES = 1001;
    //当Android6.0系统以上时，动态获取权限
    public static final String[] PERMISSIONS = new String[]{//android.permission.INTERACT_ACROSS_USERS_FULL
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.INTERNET};

    //当Android6.0系统以上时，动态获取权限
    public static final String[] mian_permission = new String[]{
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.INTERNET};

    public static final String[] READ_WRITE = new String[]{
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE};

//    android.permission.REBOOT，允许程序重新启动设备

    public static boolean hasPer(Activity activity,String[] strings){
        for (String permission : strings) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                TLog.e("未获得的权限："+permission);
                return false;
            }
        }
        return true;
    }

    public static boolean hasAllPer(Activity activity){
        String[] permissions = PERMISSIONS;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                TLog.e("未获得的权限："+permission);
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isHasInstallPermissionWithO(Context context) {
        if (context == null) {
            return false;
        }
        return context.getPackageManager().canRequestPackageInstalls();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity(Activity context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        context.startActivityForResult(intent, 1);
    }
}
