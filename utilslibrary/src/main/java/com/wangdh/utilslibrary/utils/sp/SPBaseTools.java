package com.wangdh.utilslibrary.utils.sp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/8 15:25<br>
 * 描述: 存储value值 短小用这个sp ，value较大时请使用Properties<br>
 */
public class SPBaseTools {
    private static Context context;
    private static boolean sIsAtLeastGB;
    private static String PREF_NAME = "creativelocker.pref";

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) { //android》2.3
            sIsAtLeastGB = true;
        }
    }

    public static void init(Context con) {
        context = con;
    }

    private static Context context() {
        return context;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void apply(Editor editor) {
        if (sIsAtLeastGB) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static SharedPreferences getPreferences() {
        SharedPreferences pre = context().getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return pre;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static SharedPreferences getPreferences(String prefName) {
        return context().getSharedPreferences(prefName,
                Context.MODE_PRIVATE);
    }

    public static void set(String key, boolean value) {
        Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        apply(editor);
    }

    public static void set(String key, String value) {
        Editor editor = getPreferences().edit();
        editor.putString(key, value);
        apply(editor);
    }

    public static void set(String key, int value) {
        Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        apply(editor);
    }

    public static void set(String key, long value) {
        Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        apply(editor);
    }

    public static void set(String key, float value) {
        Editor editor = getPreferences().edit();
        editor.putFloat(key, value);
        apply(editor);
    }

    public static boolean get(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public static String get(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public static int get(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public static long get(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    public static float get(String key, float defValue) {
        return getPreferences().getFloat(key, defValue);
    }
}
