package com.wangdh.utilslibrary.utils.root;

import android.os.Environment;

import com.wangdh.utilslibrary.utils.TimeUtils;
import com.wangdh.utilslibrary.utils.file.FileSDTool;
import com.wangdh.utilslibrary.utils.logger.TLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author wangdh
 * @time 2019/8/8 9:44
 * @describe
 */
public class Shell {
//    1、destroy()　　　　　　杀死这个子进程
//　　2、exitValue()　　　 　　得到进程运行结束后的返回状态
//　　3、waitFor()　　　　 　　得到进程运行结束后的返回状态，如果进程未运行完毕则等待知道执行完毕
//　　4、getInputStream()　　得到进程的标准输出信息流
//　　5、getErrorStream()　　得到进程的错误输出信息流
//　　6、getOutputStream()　得到进程的输入流
    public StringBuffer send(String cmd) {
        BufferedReader bufferedReader = null;
        String str = null;
        StringBuffer data = new StringBuffer();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);

            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            if(error!=null){
                while ((str = error.readLine()) != null)    //开始读取日志，每次读取一行
                {
                    data.append(str + "\n");
                }
            }

            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (bufferedReader == null) {
                return data;
            }
            bufferedReader.toString();
            while ((str = bufferedReader.readLine()) != null)    //开始读取日志，每次读取一行
            {
                data.append(str + "\n");
            }
            process.waitFor();
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


    // 当
    public void recordLog() throws Exception {
        TLog.e("开始记录日志");
        String l = FileSDTool.getSDPath() + "A_Log/";
        String currentTimeFor2 = TimeUtils.getCurrentTimeFor2();
        String s = "all_Log_" + currentTimeFor2 + ".txt";
        new FileSDTool().makeFilePath(l, s);
        String cmd = "logcat -v time -f " + l + s;
        TLog.e("shell:"+cmd);
        send(cmd);
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
