package com.wangdh.utilslibrary.utils;

import android.content.Context;

import com.apkfuns.log2file.LogFileEngineFactory;
import com.apkfuns.logutils.LogUtils;
import com.wangdh.utilslibrary.config.AppConfig;

/**
 * @author wangdh
 * @date 2020/1/18 13:07
 * 描述:
 * options
 * 方法	描述	取值	缺省
 * configAllowLog	是否允许日志输出	boolean	true
 * configTagPrefix	日志log的前缀	String	"LogUtils"
 * configShowBorders	是否显示边界	boolean	false
 * configLevel	日志显示等级	LogLevelType	LogLevel.TYPE_VERBOSE
 * addParserClass	自定义对象打印	Parser	无
 * configFormatTag	个性化设置Tag	String	%c{-5}
 * configMethodOffset	方法偏移量	int	0
 */
public class TLog {
    public static boolean isTest = true;
    public static boolean isDebug = true;

    public static void init(Context context) {
        LogUtils.getLogConfig()
                .configAllowLog(true)
                .configTagPrefix(context.getPackageName())
                .configShowBorders(false)
                .configMethodOffset(1)
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}");


        LogUtils.getLog2FileConfig().configLog2FileEnable(true)
                .configLog2FilePath(AppConfig.DEFAULT_SAVE_LOG_PATH)
                .configLog2FileNameFormat("%d{yyyyMMdd}.txt")
                .configLogFileEngine(new LogFileEngineFactory(context))
        ;

    }

    public static void e(String tag, String msg) {
        LogUtils.e(tag, msg);
    }

    public static void e(Object msg) {
        LogUtils.e(msg);
    }

    public static void d(String tag, String msg) {
        LogUtils.d(tag, msg);
    }

    public static void d(Object msg) {
        LogUtils.d(msg);
    }

}
