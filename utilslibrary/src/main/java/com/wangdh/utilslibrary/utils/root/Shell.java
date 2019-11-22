package com.wangdh.utilslibrary.utils.root;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import com.wangdh.utilslibrary.utils.TimeUtils;
import com.wangdh.utilslibrary.utils.file.FileSDTool;
import com.wangdh.utilslibrary.utils.logger.TLog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangdh
 * @time 2019/8/8 9:44
 * @describe 当子进程被杀死 application 会重启
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
            if (error != null) {
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

    /**
     * 执行shell指令
     *
     * @param strings 指令集
     * @return 指令集是否执行成功
     */
    public StringBuffer su(String... strings) {
        BufferedReader bufferedReader = null;
        String str = null;
        StringBuffer data = new StringBuffer();
        Process process = null;
        try {
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            for (String s : strings) {
                outputStream.writeBytes(s + "\n");
                outputStream.flush();
            }
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            if (error != null) {
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


    // 持续记录日志
    public void recordLog() throws Exception {
        // 在5.1系统 之前， 进程不会随着 app 进程死亡而死亡， 所以再次启动的时候必须手动杀掉 cmd进程
        killAll();
        TLog.e("开始记录日志");
        String l = FileSDTool.getSDPath() + "A_Log/";
        String currentTimeFor2 = TimeUtils.getCurrentTimeFor2();
        String s = "all_Log_" + currentTimeFor2 + ".txt";
//        String s = "all_Log.txt";
        new FileSDTool().makeFilePath(l, s);
        String cmd = "logcat -v time -f " + l + s;
        TLog.e("shell:" + cmd);
        StringBuffer send = send(cmd);
        TLog.e("shell return:" + send.toString());
    }

    /**
     * 删除所有 cmd 进程
     */
    public void killAll() {
        StringBuffer ps = send("ps");
        List<String[]> datas = StringToList(ps.toString(), 9);
        int appid = android.os.Process.myPid();
        String appUName = "";
        String appPPid = "";

        TLog.e("本程序进程id：" + appid);
        for (String[] data : datas) {
            try {
                boolean b = Integer.valueOf(data[1]) == appid;
                if (b) {
                    appUName = data[0];
                    appPPid = data[2];
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (String[] data : datas) {
            try {
//                        TLog.e("进程:" + data[0] + " " + data[1] + " " + data[2] + " " + data[3] + " " + data[4] + " " + data[5] + " " + data[6] + " " + data[7] + " " + data[8]);
                boolean b = Integer.valueOf(data[2]) == appid;
                if (b) {
                    android.os.Process.killProcess(Integer.valueOf(data[1]));
                    TLog.e("结束进程:" + data[0] + " " + data[1] + " " + data[2] + " " + data[3] + " " + data[4] + " " + data[5] + " " + data[6] + " " + data[7] + " " + data[8]);
                    continue;
                }
                if (!TextUtils.isEmpty(appUName) && appUName.equals(data[0]) && !appPPid.equals(data[2])) {
                    android.os.Process.killProcess(Integer.valueOf(data[1]));
                    TLog.e("结束进程:" + data[0] + " " + data[1] + " " + data[2] + " " + data[3] + " " + data[4] + " " + data[5] + " " + data[6] + " " + data[7] + " " + data[8]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    //只能杀死 特定的进程， 比如 主进程 和 service 进程。 cmd创建的进程 无法看到
    public void killAll(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
            String s = "";
            int pid = runningAppProcess.pid;
            String processName = runningAppProcess.processName;
            s = s + processName + " " + pid;
            if (android.os.Process.myPid() != pid) {
                android.os.Process.killProcess(pid);
                TLog.e("结束进程：" + s);
            }
        }
    }

    public void appProcess(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
            int pid = runningAppProcess.pid;
            String processName = runningAppProcess.processName;
            if (android.os.Process.myPid() == pid) {
//                runningAppProcess.
                return;
            }
        }
    }

    /**
     * \
     *
     * @param msg
     * @param line 列 数 ，从1开始计数； 不等于这个列数的 就不会录入
     * @return
     */
    public List<String[]> StringToList(String msg, int line) {
        String[] itme = msg.split("\n");
        List<String[]> all = new ArrayList<>();
        for (String it : itme) {

            String[] s = it.split(" ");
            List<String> zz = new ArrayList<>();
            for (int i = 0; i < s.length; i++) {
                if (!TextUtils.isEmpty(s[i])) {
                    if (zz.size() == line) {
                        String last = zz.get(line - 1) + "" + s[i];
                        zz.set(line - 1, last);
                    } else {
                        zz.add(s[i]);
                    }
                }
            }

            if (zz.size() == line) {
//                String ll = "";
//                for (String zz1 : zz) {
//                    ll = ll + "-" + zz1;
//                }
//                TLog.e(zz.size() + ":" + ll);
                all.add(zz.toArray(new String[zz.size()]));
            }
        }
        return all;
    }
}
