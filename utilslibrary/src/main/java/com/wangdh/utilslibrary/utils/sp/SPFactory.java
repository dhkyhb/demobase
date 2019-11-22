package com.wangdh.utilslibrary.utils.sp;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangdh on 2017/9/28.
 * name：
 * 描述：2017年10月23日 09:34:27
 */
@Deprecated
public class SPFactory {
    private static Map<String, Class> map = new HashMap<>();
    // 默认 如果是select 属性 get 取出的是 value 如果没value 会取出key
    private static boolean isGetSelectKey = false;
    //默认属性
    //数字类型数据 取出出错，还原默认值： 比如默认类型String 值为 a，这个时候取出int类型会异常 应该还原这个值
    private static String errorIntDef = "0";
    private static String errorDoubleDef = "0";
    private static String errorLongDef = "0";
    private static String errorFloatDef = "0";

    private static boolean errorBooleanDef = false;

    //默认boolean 存入或者取出 String int
    private static String[] _true_string = new String[]{"1", "true"};
    private static int[] _true_int = new int[]{1};
    //如果为默认类型为boolean， 传递进来的数据为一下2种 ，那么对比 true 属性是否匹配 如果是 就true保存 否者其他的全部都是false
    //反之 默认类型为 int 或者 String，传递进来为 boolean 也会保存 下标为0 的元素，
    private static String[] _false_string = new String[]{"0", "false"};
    private static int[] _false_int = new int[]{0};

    //默认String 存入或者取出 int folat doule
    public static boolean match(String regex, Object str) {
        if (TextUtils.isEmpty(regex)) {
            return true;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(String.valueOf(str));
        return matcher.matches();
    }

    public static String getFieldName(String k) throws Exception {
        int lastIndexOf = k.lastIndexOf(".");
        String fieldName = k.substring(lastIndexOf + 1);
        return fieldName;
    }

    public static String getClassName(String k) throws Exception {
        int lastIndexOf = k.lastIndexOf(".");
        String class_key = k.substring(0, lastIndexOf);
        return class_key;
    }

    public static Class getClazz(String k) throws Exception {
        String className = getClassName(k);
        Class cla = map.get(className);
        if (cla == null) {
            cla = Class.forName(className);
            System.out.println("创建：" + cla.getName());
            map.put(className, cla);
        }
        return cla;
    }

    public static Field getArgField(String k) throws Exception {
        Class clazz = getClazz(k);
        Field field = clazz.getField(getFieldName(k));
        return field;
    }

    public static SP getAnno(String k) throws Exception {
        SP annotation = getArgField(k).getAnnotation(SP.class);
        return annotation;
    }

    public static SP getAnno(Field field) throws Exception {
        SP annotation = field.getAnnotation(SP.class);
        return annotation;
    }

    public static Class getType(String k) {
        SP anno = null;
        try {
            anno = getAnno(k);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return anno.type();
    }

    public static Class getType(Field field) {
        SP anno = null;
        try {
            anno = getAnno(field);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return anno.type();
    }

    public static boolean isSelect(String k) {
        SP anno = null;
        try {
            anno = getAnno(k);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        String[] select = anno.selectValue();
        if (select == null || select.length <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isSelect(Field field) {
        SP anno = null;
        try {
            anno = getAnno(field);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        String[] select = anno.selectValue();
        if (select == null || select.length <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public static String[] getSelectValue(String k) {
        SP anno = null;
        try {
            anno = getAnno(k);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] select = anno.selectValue();
        return select;
    }

    public static String[] getSelectKey(String k) {
        SP anno = null;
        try {
            anno = getAnno(k);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] select = anno.selectKey();
        return select;
    }

    public static boolean isChange(String k) throws Exception {
        SP anno = null;
        anno = getAnno(k);
        boolean change = anno.change();
        return change;
    }

    public static boolean isChange(Field field) throws Exception {
        SP anno = null;
        anno = getAnno(field);
        boolean change = anno.change();
        return change;
    }

    public static String getHint(String k) throws Exception {
        SP anno = null;
        anno = getAnno(k);
        String hint = anno.hint();
        return hint;
    }

    public static String getHint(Field field) throws Exception {
        SP anno = null;
        anno = getAnno(field);
        String hint = anno.hint();
        return hint;
    }

    public static boolean set(String k, Object obj) {
        try {
            Log.e("save SP:", "KEY:" + k);
            Log.e("save SP:", "Value:" + String.valueOf(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }

        SP annotation = null;
        try {
            annotation = getAnno(k);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Class defType = annotation.type();
        Class type = obj.getClass();

        boolean isSave = false;
        if (isNumber(type)) {
            String obj_string = String.valueOf(obj);
            if (isInt(defType)) {
                isSave = true;
                SPBaseTools.set(k, Integer.parseInt(obj_string));
            }
            if (isFloat(defType)) {
                isSave = true;
                SPBaseTools.set(k, Float.parseFloat(obj_string));
            }
            if (isLong(defType)) {
                isSave = true;
                SPBaseTools.set(k, Long.parseLong(obj_string));
            }
            if (isString(defType)) {
                isSave = true;
                SPBaseTools.set(k, obj_string);
            }
            if (isBoolean(defType)) {
                boolean b = checkBoolean(obj_string);
                SPBaseTools.set(k, b);
            }
        } else if (isBoolean(type)) {
            if (isBoolean(defType)) {
                isSave = true;
                SPBaseTools.set(k, (boolean) obj);
            } else if (isString(defType)) {
                isSave = true;
                SPBaseTools.set(k, getStringByBoolean(obj));
            } else if (isInt(defType)) {
                isSave = true;
                SPBaseTools.set(k, getIntByBoolean(obj));
            }
        } else if (isString(type)) {
            String[] selectKey = annotation.selectKey();
            String[] selectValue = annotation.selectValue();
            boolean b = selectKey != null && selectKey.length > 0;
            boolean b1 = selectValue != null && selectValue.length > 0;
            if (b || b1) {
                if (b) {
                    for (int i = 0; i < selectKey.length; i++) {
                        if (selectKey[i].equals(obj)) {
                            SPBaseTools.set(k, selectKey[i]);
                            return true;
                        }
                    }
                }
                //证明是选择类型
                if (b1) {
                    //证明是有关系对应的
                    for (int i = 0; i < selectValue.length; i++) {
                        if (selectValue[i].equals(obj)) {
                            SPBaseTools.set(k, selectKey[i]);
                            return true;
                        }
                    }
                }
            }

            if (isString(defType)) {
                isSave = true;
                SPBaseTools.set(k, (String) obj);
            } else if (isBoolean(defType)) {
                isSave = true;
                boolean b2 = checkBoolean(obj);
                SPBaseTools.set(k, b2);
            } else if (isNumber(defType)) {
                if (isInt(defType)) {
                    isSave = true;
                    SPBaseTools.set(k, Integer.parseInt(String.valueOf(obj)));
                }
                if (isFloat(defType)) {
                    isSave = true;
                    SPBaseTools.set(k, Float.parseFloat(String.valueOf(obj)));
                }
                if (isLong(defType)) {
                    isSave = true;
                    SPBaseTools.set(k, Long.parseLong(String.valueOf(obj)));
                }
            }
        } else {
            log("sp无法设置除基本以外的类型");
            return false;
        }
        if (isSave) {

        } else {
            log("设置的数据 与默认定义类型不一相同！");
            log("设置数据类型：" + type);
            log("定义数据类型：" + defType);
            return false;
        }
        return true;
    }

    //兼容类型变更出现的异常 ：远类型为 “1” 现在取出2
    //获取“默认类型”的值
    public static Object get(String k) throws Exception {
        SP annotation = getAnno(k);
        String defValue = annotation.value();//默认值
        Class type = annotation.type();
        Object saveValue = null;

        if (isInt(type)) {
            try {
                saveValue = SPBaseTools.get(k, Integer.valueOf(defValue));
            } catch (Exception e) {
                if (e.getClass().equals(NumberFormatException.class)) {
                    SPBaseTools.set(k, Integer.valueOf(String.valueOf(errorIntDef)));
                    saveValue = Integer.valueOf(String.valueOf(errorIntDef));
                }
            }

        } else if (isBoolean(type)) {
            boolean b = checkBoolean(defValue);
            try {
                saveValue = SPBaseTools.get(k, b);
            } catch (Exception e) {

                if (e.getClass().equals(ClassCastException.class)) {
                    try {
                        if (e.getClass().equals(ClassCastException.class)) {
                            //尝试取出int类型数据
                            saveValue = SPBaseTools.get(k, getIntByBoolean(b));
                            saveValue = checkBoolean(saveValue);
                            SPBaseTools.set(k, (boolean) saveValue);
                        }
                    } catch (Exception ee) {

                        if (ee.getClass().equals(ClassCastException.class)) {
                            saveValue = SPBaseTools.get(k, getStringByBoolean(b));
                            saveValue = checkBoolean(saveValue);
                            SPBaseTools.set(k, (boolean) saveValue);
                        }

                    }

                }

            }

        } else if (isString(type)) {
            saveValue = SPBaseTools.get(k, String.valueOf(defValue));
        } else if (isLong(type)) {
            try {
                saveValue = SPBaseTools.get(k, Long.valueOf(defValue));
            } catch (Exception e) {
                if (e.getClass().equals(NumberFormatException.class)) {
                    saveValue = Long.valueOf(String.valueOf(errorLongDef));
                    SPBaseTools.set(k, Long.valueOf(String.valueOf(errorLongDef)));
                }
            }
        } else if (isFloat(type)) {
            try {
                saveValue = SPBaseTools.get(k, Float.valueOf(defValue));
            } catch (Exception e) {
                if (e.getClass().equals(NumberFormatException.class)) {
                    SPBaseTools.set(k, Float.valueOf(String.valueOf(errorFloatDef)));
                    saveValue = Float.valueOf(String.valueOf(errorFloatDef));
                }
            }
        }
        return saveValue;
    }

    public static <T> T get(String k, Class<T> t) {
        try {
            Object o = get(k);
            try {
                Log.e("SP:", "get KEY:" + k);
                Log.e("SP:", "get Value:" + String.valueOf(o));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Class<?> defType = o.getClass();

            if (isNumber(defType) || isString(defType)) {
                String[] selectKey = getSelectKey(k);
                if (selectKey != null && selectKey.length > 0) {
                    for (int i = 0; i < selectKey.length; i++) {
                        if (o.equals(selectKey[i])) {
                            String[] selectValue = getSelectValue(k);
                            if (selectValue != null && selectValue.length > 0 && !isGetSelectKey) {
                                o = selectValue[i];
                            } else {
                                o = selectKey[i];
                            }
                            return (T) o;
                        }
                    }
                }
                if (isInt(t)) {
                    o = Integer.parseInt(String.valueOf(o));
                } else if (isFloat(t)) {
                    o = Float.parseFloat(String.valueOf(o));
                } else if (isLong(t)) {
                    o = Long.parseLong(String.valueOf(o));
                } else if (isString(t)) {
                    o = String.valueOf(o);
                } else if (isBoolean(t)) {
                    boolean b = checkBoolean(defType);
                    o = b;
                }
            } else if (isBoolean(defType)) {
                if (isBoolean(t)) {
                    return (T) o;
                } else if (isString(t)) {
                    o = getStringByBoolean(o);
                } else if (isNumber(t)) {
                    o = getIntByBoolean(o);
                }
            }
            return (T) o;
        } catch (Exception e) {
            System.out.println("警告-警告-警告-警告-警告-警告-警告-警告-警告");
            System.out.println("获取数据出错：" + k);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 恢复默认设置
     *
     * @param k
     */
    public static void RestoreDefault(String k) {
        SP anno = null;
        try {
            anno = getAnno(k);
        } catch (Exception e) {
            e.printStackTrace();
        }
        set(k, anno.value());
    }

    /**
     * 性能不好 最好 做个防止多次点击 并且等待
     *
     * @param clazz
     */
    public static void RestoreDefault(Class clazz) {
        try {
            Object o = clazz.newInstance();
            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                String key = (String) field.get(o);
                RestoreDefault(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getInt(String k) {
        return get(k, Integer.class);
    }

    public static boolean getBoolean(String k) {
        return get(k, Boolean.class);
    }

    public static String getString(String k) {
        return get(k, String.class);
    }

    public static long getLong(String k) {
        return get(k, Long.class);
    }

    public static float getFloat(String k) {
        return get(k, Float.class);
    }

    private static boolean isBoolean(Class<?> type) {
        if (type.equals(Boolean.class) || type.equals(boolean.class)) {
            return true;
        }
        return false;
    }

    private static boolean isInt(Class<?> type) {
        if (type.equals(int.class) || type.equals(Integer.class)) {
            return true;
        }
        return false;
    }

    private static boolean isFloat(Class<?> type) {
        if (type.equals(Float.class) || type.equals(float.class)) {
            return true;
        }
        return false;
    }

    private static boolean isLong(Class<?> type) {
        if (type.equals(Long.class) || type.equals(long.class)) {
            return true;
        }
        return false;
    }

    private static boolean isNumber(Class<?> type) {
        if (isLong(type) || isFloat(type) || isInt(type)) {
            return true;
        }
        return false;
    }

    private static boolean isString(Class<?> type) {
        if (type.equals(String.class)) {
            return true;
        }
        return false;
    }

    /**
     * 判断 true false
     *
     * @param o
     * @return
     */
    private static boolean checkBoolean(Object o) {
        if (isBoolean(o.getClass())) {
            Boolean aBoolean = Boolean.valueOf(String.valueOf(o));
            return aBoolean;
        }
        for (String s : _true_string) {
            if (s.equals(o)) {
                return true;
            }
        }
        try {
            for (int i : _true_int) {
                if (i == Integer.parseInt(String.valueOf(o))) {
                    return true;
                }
            }
        } catch (Exception e) {
        }

        return false;
    }

    private static int getIntByBoolean(Object o) {
        boolean b = checkBoolean(o);
        if (b) {
            return _true_int[0];
        } else {
            return _false_int[0];
        }
    }

    private static String getStringByBoolean(Object o) {
        boolean b = checkBoolean(o);
        if (b) {
            return _true_string[0];
        } else {
            return _false_string[0];
        }
    }

    private static void log(String msg) {
        Log.e("SP:", msg);
    }
}