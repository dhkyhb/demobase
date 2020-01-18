package com.wangdh.utilslibrary.netlibrary.dowload;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class DownloadTaskManage {
    // key: 下载路径 value:时间
    private static Map<String, String> taskList = new HashMap<>();

    public static boolean isDowload(String videoId) {
        String s = taskList.get(videoId);
        if (TextUtils.isEmpty(s)) {
            //为null 表示没有下载的任务
            return false;
        }
        //如果存在 则判断下载时间 是否 大于20分钟  如果大于 就当作没有此任务
        Long aLong = Long.valueOf(s);
        long l = System.currentTimeMillis();
        long l1 = l - aLong;
        long l2 = 1000L * 60 * 20;
        return !(l1 > l2);
    }

    public static void addTask(String url) {
        taskList.put(url, String.valueOf(System.currentTimeMillis()));
    }

    public static void deleteTask(String url) {
        taskList.remove(url);
    }
}
