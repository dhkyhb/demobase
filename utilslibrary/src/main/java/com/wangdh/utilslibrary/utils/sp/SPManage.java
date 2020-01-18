package com.wangdh.utilslibrary.utils.sp;

/**
 * @author wangdh
 * @date 2020/1/18 11:23
 * 描述: sp 存储入口
 */
public class SPManage {
    public static String get(String key) {
        return new SPClient().get(key);
    }

    public static int getInt(String key) {
        return new SPClient().getInt(key);
    }

    public static boolean getBoolean(String key) {
        return new SPClient().getBoolean(key);
    }

    public static float getFloat(String key) {
        return new SPClient().getFloat(key);
    }

    public static long getLong(String key) {
        return new SPClient().getLong(key);
    }

    public static void set(String key, String value) {
        SPClient.set(key, value);
    }

    public static void set(String key, boolean value) {
        SPClient.set(key, value);
    }

    public static void set(String key, long value) {
        SPClient.set(key, value);
    }

    public static void set(String key, int value) {
        SPClient.set(key, value);
    }

    public static void set(String key, float value) {
        SPClient.set(key, value);
    }

}
