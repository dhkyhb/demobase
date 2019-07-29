package com.wangdh.utilslibrary.utils.sp;

import java.lang.reflect.Field;
import java.util.UUID;

public class SPClient {
    public String get(String key) {
        String uuid = UUID.randomUUID().toString();
        String value = SPBaseTools.get(key, uuid);
        if (!uuid.equals(value)) {
            return value;
        }
        keyName = key;
        try {
            initData();
            value = annotation.value();
            SPBaseTools.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return value;
    }

    private String className;
    private String fieldName;
    private String keyName;
    private SP annotation;

    public void initData() throws Exception {
        int lastIndexOf = keyName.lastIndexOf(".");
        className = keyName.substring(0, lastIndexOf);
        fieldName = keyName.substring(lastIndexOf + 1);

        Class<?> aClass = Class.forName(className);
        Field field = aClass.getField(fieldName);
        annotation = field.getAnnotation(SP.class);
    }

    public int getInt(String key) {
        return Integer.valueOf(get(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.valueOf(get(key));
    }

    public float getFloat(String key) {
        return Float.valueOf(get(key));
    }

    public long getLong(String key) {
        return Long.valueOf(get(key));
    }

    public static void set(String key, String value) {
        SPBaseTools.set(key, value);
    }

    public static void set(String key, boolean value) {
        set(key, String.valueOf(value));
    }

    public static void set(String key, long value) {
        set(key, String.valueOf(value));
    }

    public static void set(String key, int value) {
        set(key, String.valueOf(value));
    }

    public static void set(String key, float value) {
        set(key, String.valueOf(value));
    }
}
