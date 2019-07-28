package com.wdh.sptest.sp;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SPManage {
    //默认String 存入或者取出 int folat doule
    public static boolean match(String regex, Object str) {
        if (TextUtils.isEmpty(regex)) {
            return true;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(String.valueOf(str));
        return matcher.matches();
    }

    public static String get(String key){
        return new SPClient().get(key);
    }
    public static int getInt(String key){
        return new SPClient().getInt(key);
    }
    public static boolean getBoolean(String key){
        return new SPClient().getBoolean(key);
    }
    public static float getFloat(String key){
        return new SPClient().getFloat(key);
    }
    public static long getLong(String key){
        return new SPClient().getLong(key);
    }

    public static void set(String key,String value){
        new SPClient().set(key,value);
    }
    public static void set(String key,boolean value){
        new SPClient().set(key,value);
    }
    public static void set(String key,long value){
        new SPClient().set(key,value);
    }
    public static void set(String key,int value){
        new SPClient().set(key,value);
    }
    public static void set(String key,float value){
        new SPClient().set(key,value);
    }

}
