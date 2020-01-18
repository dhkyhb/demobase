package com.wangdh.utilslibrary.utils;

import android.util.Log;

import com.wangdh.utilslibrary.dblibrary.dao.UtilsDao;
import com.wangdh.utilslibrary.dblibrary.entity.AppVersionMsg;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class InstallAPK {
    public static void install() {
        AppVersionMsg versionByNew = UtilsDao.getVersionByNew();
        if (versionByNew == null) {
            return;
        }
        String fileUrl = versionByNew.getLocalUrl() + versionByNew.getFileName();
        TLog.e("fileUrl:"+fileUrl);
        File file = new File(fileUrl);
        if (file == null) {
            return;
        }
        long length = file.length();
        if (length == 0L) {
            return;
        }
        if (length == versionByNew.getFileSize()) {
            silentInstall(fileUrl);
        }
    }

    /**
     * 静默更新
     * 需要系统安装权限
     *
     * @param apkPath
     * @return
     */
    public static boolean silentInstall(String apkPath) {
        TLog.e("静默安装：" + apkPath);
        boolean result = false;
        DataOutputStream dataOutputStream = null;
        BufferedReader errorStream = null;
        BufferedReader successStream = null;
        Process process = null;
        try {
            // 申请 su 权限
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            // 执行 pm install 命令
            String command = "pm install -r " + apkPath + "\n";
            dataOutputStream.write(command.getBytes(Charset.forName("UTF-8")));
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorMsg = new StringBuilder();
            String line;
            while ((line = errorStream.readLine()) != null) {
                errorMsg.append(line);
            }
            TLog.e("silent install error message:{}" + errorMsg);
            StringBuilder successMsg = new StringBuilder();
            successStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // 读取命令执行结果
            while ((line = successStream.readLine()) != null) {
                successMsg.append(line);
            }
            TLog.e("silent install success message:{}" + successMsg);
            // 如果执行结果中包含 Failure 字样就认为是操作失败，否则就认为安装成功
            if (!(errorMsg.toString().contains("Failure") || successMsg.toString().contains("Failure"))) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (process != null) {
                    process.destroy();
                }
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
                if (successStream != null) {
                    successStream.close();
                }
            } catch (Exception e) {
                // ignored
            }
        }
        return result;
    }

    /**
     * 执行具体的静默安装逻辑，需要手机ROOT。
     *
     * @param apkPath 要安装的apk文件的路径
     * @return 安装成功返回true，安装失败返回false。
     */
    public static boolean install(String apkPath) {
        boolean result = false;
        DataOutputStream dataOutputStream = null;
        BufferedReader errorStream = null;
        try {
            // 申请su权限
            Process process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            // 执行pm install命令
            String command = "pm install -r " + apkPath + "\n";
            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String msg = "";
            String line;
            // 读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg += line;
            }
            Log.d("TAG", "install msg is " + msg);
            // 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功
            if (!msg.contains("Failure")) {
                result = true;
            }
        } catch (Exception e) {
            Log.e("TAG", e.getMessage(), e);
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
            } catch (IOException e) {
                Log.e("TAG", e.getMessage(), e);
            }
        }
        return result;
    }

}
