package com.wangdh.utilslibrary.utils.root;

import com.wangdh.utilslibrary.utils.TimeUtils;
import com.wangdh.utilslibrary.utils.file.FileSDTool;
import com.wangdh.utilslibrary.utils.logger.TLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * @author wangdh
 * @time 2019/8/8 9:44
 * @describe
 */
public class Shell {
    public StringBuffer send(String cmd) {
        BufferedReader bufferedReader = null;
        String str = null;
        StringBuffer data = new StringBuffer();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (bufferedReader == null) {
                return data;
            }
            bufferedReader.toString();
            while ((str = bufferedReader.readLine()) != null)    //开始读取日志，每次读取一行
            {
                data.append(str + "\n");
            }
        } catch (Exception e) {
            data.append(e.getMessage());
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return data;
    }

    public StringBuffer writeLog() throws Exception {
        TLog.e("开始写入日志");
        Shell shell = new Shell();
        StringBuffer log = shell.send("logcat -d");
        shell.send("logcat -c");
        String filePath = FileSDTool.getSDPath() + "A_log" + File.separator;
        String fileName = "log.txt";
        new FileSDTool().writeTxtToFile(log, filePath, fileName);
        TLog.e("写入日志成功");
        return log;
    }

    public void recordLog() throws Exception {
        TLog.e("开始记录日志");
        String l = FileSDTool.getSDPath() + "A_Log/";
        String currentTimeFor2 = TimeUtils.getCurrentTimeFor2();
        String s = "all_Log_" + currentTimeFor2 + ".txt";
        new FileSDTool().makeFilePath(l, s);
        send("logcat -v time > " + l + s);
    }

    public boolean isRun(String pName) {
        StringBuffer cmd = send("ps " + pName);
        TLog.e(cmd.toString());
        if (cmd != null) {
            boolean contains = cmd.toString().contains(pName);
            if (contains) {
                return true;
            }
        }
        return false;
    }
}
