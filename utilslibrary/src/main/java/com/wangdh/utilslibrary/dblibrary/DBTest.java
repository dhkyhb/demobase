package com.wangdh.utilslibrary.dblibrary;

import com.wangdh.utilslibrary.dblibrary.db.SQL_LogText;
import com.wangdh.utilslibrary.dblibrary.entity.LogText;
import com.wangdh.utilslibrary.utils.TimeUtils;
import com.wangdh.utilslibrary.utils.TLog;

import java.util.List;

/**
 * @author wangdh
 * @time 2019/2/19 16:21
 * @describe 测试
 */
public class DBTest {
    public static void insert() {
        LogText logText = new LogText();
        logText.setName("运行次数");
        logText.setTime(TimeUtils.getCurrentTimeFor2());
        logText.setText("点击了 点击了。");
        SQL_LogText.getInstance().insert(logText);
        List<LogText> select = SQL_LogText.getInstance().select();
        for (LogText text : select) {
            TLog.e(text.toString());
        }
    }

}