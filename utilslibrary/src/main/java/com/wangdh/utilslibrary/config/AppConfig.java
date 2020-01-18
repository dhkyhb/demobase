package com.wangdh.utilslibrary.config;

import android.content.Context;
import android.os.Environment;

import com.wangdh.utilslibrary.BuildConfig;

import java.io.File;

/**
 * @author wangdh
 * @date 2020/1/18 11:17
 * 描述:
 */
public class AppConfig {
    /**
     * 项目默认sd卡储存路径
     */
    public static String APP_PATH;
    /**
     * 默认存放图片的路径
     */
    public static String DEFAULT_SAVE_IMAGE_PATH;
    /**
     * 默认存放下载文件的路径
     */
    public static String DEFAULT_SAVE_FILE_PATH;
    /**
     * 默认存放日志文件的路径
     */
    public static String DEFAULT_SAVE_LOG_PATH;


    public static void init(Context context) {
        APP_PATH = Environment.getExternalStorageDirectory() + File.separator + context.getPackageName() + File.separator;
        DEFAULT_SAVE_IMAGE_PATH = APP_PATH + File.separator + "img" + File.separator;
        DEFAULT_SAVE_FILE_PATH = APP_PATH + File.separator + "download" + File.separator;
        DEFAULT_SAVE_LOG_PATH = APP_PATH + File.separator + "log" + File.separator;
    }
}
