package com.wangdh.utilslibrary.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangdh on 2018/1/16.
 * name：
 * 描述：
 */
public class TimeUtils {

    public static String formatDateYDT(String yyyyMMddHHmmss) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            return sdf2.format(sdf1.parse(yyyyMMddHHmmss));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将时间转换为时间戳
     * @param s 2019/07/29 比如这样的
     * @param format yyy/MM/dd
     * @return
     * @throws ParseException
     */
    public static long dateToStamp(String s,String format) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        return ts;
    }

    /**
     * 获取年份
     *
     * @return
     */
    public static String getYear() {
        return getNowTimeByFormat("yyyy");
    }

    /**
     * 获取日期
     *
     * @return MMDD
     */
    public static String getSysDate() {
        return getNowTimeByFormat("MMdd");
    }

    /**
     * 获取时间
     *
     * @return hhmmss
     */
    public static String getSysTime() {
        return getNowTimeByFormat("HHmmss");
    }

    /**
     * pos  显示 格式化时间
     *
     * @param MMddHHmmss
     * @return yyyy/MM/dd HH:mm:ss
     */
    public static String formatDate(String MMddHHmmss) {
        return formatDateYDT(getYear() + MMddHHmmss);
    }

    public static String getNowTimeByFormat(String f) {
        SimpleDateFormat sdf = new SimpleDateFormat(f);
        return sdf.format(new Date());
    }
    public static final String format_1 = "yyyy-MM-dd HH:mm:ss";
    public static final String format_2 = "yyyyMMddHHmmss";
    public static final String format_3 = "HH:mm:ss";

    public static String getTime(long time,String format){
        String res ="";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String getCurrentTime(String format){
        long time = System.currentTimeMillis();
        String time1 = getTime(time, format);
        return time1;
    }

    public static String getCurrentTimeFor1() {
        return getCurrentTime(format_1);
    }
    public static String getCurrentTimeFor2() {
        return getCurrentTime(format_2);
    }

    public static String getNowStrTime() {
        long time = System.currentTimeMillis();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }
}
