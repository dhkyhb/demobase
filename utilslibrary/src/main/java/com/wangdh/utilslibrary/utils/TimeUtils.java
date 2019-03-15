package com.wangdh.utilslibrary.utils;

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

    public static String hhmmss() {
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
        String format = ft.format(now);
        return format;
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
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
}
