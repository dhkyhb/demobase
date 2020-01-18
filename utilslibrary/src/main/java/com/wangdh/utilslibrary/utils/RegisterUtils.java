package com.wangdh.utilslibrary.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.wangdh.utilslibrary.R;
import com.wangdh.utilslibrary.config.UtilsConfig;
import com.wangdh.utilslibrary.utils.file.FileSDTool;
import com.wangdh.utilslibrary.utils.sp.SPManage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class RegisterUtils {
    public static final String key = "RegisterUtils_register";
    private static String path = Environment
            .getExternalStorageDirectory()
            + File.separator + "System" + File.separator;
    private static String name = "system_register.txt";

    //初始化获取包名定义路径
    public static void init(Context context) {
        String packageName = context.getApplicationInfo().packageName;
        TLog.e("packageName:" + packageName);
        path = Environment
                .getExternalStorageDirectory()
                + File.separator + "System" + File.separator + packageName + File.separator;
    }

    public static String getSoleCode() {
        String s = SPTools.get(key, "");
        return s;
    }

    /**
     * 平衡 文件和app 的属性
     *
     * @throws Exception
     */
    public static void initRegisterFile() throws Exception {
        if (!isHas()) {
            //不存在就创建
            FileSDTool fileSDTool = new FileSDTool();
            fileSDTool.makeFilePath(path, name);
            writeRegister(getInitJson());
        } else {
            //文件中为null 就去 app中取，并且写入到文件
            String s = readRegister();
            if (TextUtils.isEmpty(s)) {
                writeRegister(getInitJson());
            } else {
                try {
                    Map<String, String> map = new Gson().fromJson(s, Map.class);
                    String s1 = map.get(key_id);
                    if (!TextUtils.isEmpty(s1)) {
                        SPManage.set(UtilsConfig.appId, s1);
                    } else {
                        writeRegister(getInitJson());
                    }

                    String code = map.get(key_code);
                    if (!TextUtils.isEmpty(s1)) {
                        SPManage.set(UtilsConfig.activationCode, code);
                    } else {
                        writeRegister(getInitJson());
                    }
                } catch (Exception e) {
                    writeRegister(getInitJson());
                }
            }
        }
    }

    public static boolean isHas() {
        File file = null;
        file = new File(path + name);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static String readRegister() {
        FileInputStream fin = null;
        BufferedReader buffReader = null;
        StringBuffer sb = new StringBuffer();
        try {
            fin = new FileInputStream(path + name);

            InputStreamReader reader = new InputStreamReader(fin);
            buffReader = new BufferedReader(reader);
            String strTmp = "";
            while ((strTmp = buffReader.readLine()) != null) {
                System.out.println(strTmp);
                sb.append(strTmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buffReader.close();
            } catch (Exception e) {
            }
        }
        return sb.toString();
    }

    public static void writeRegister(String msg) throws Exception {
        File file = new File(path + name);
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(msg);
        out.close();
    }

    /**
     * 是否激活
     *
     * @return
     */
    public static boolean isActivation() {
        String appId = SPManage.get(UtilsConfig.appId);
        String activationCode = SPManage.get(UtilsConfig.activationCode);
        if (TextUtils.isEmpty(appId)) {
            try {
                initRegisterFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            isActivation();
        }
        if (TextUtils.isEmpty(activationCode)) {
            return false;
        }
        appId = appId + "qqt123456";
        String s = stringToMD5(appId);
        if (activationCode.equals(s)) {
            return true;
        }
        return false;
    }

    //{"code":"2555a3389e1e25fbe7efc9fcb53ebeec","id":"ab0517a3-7c0f-4128-a466-a73245ed0b25"}

    /**
     * 本地校验
     *
     * @param msg       对比值
     * @param secretKey 密钥
     * @return
     */
    public static boolean register(String msg, String secretKey) {
        String appId = SPManage.get(UtilsConfig.appId);
        TLog.e("appId:" + appId);
        TLog.e("activationCode:" + msg);
        String activationCode = msg;
        try {
            initRegisterFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(activationCode)) {
            return false;
        }
        appId = appId + "qqt123456" + secretKey;
        String s = stringToMD5(appId);
//        TLog.e("md5:" + s);
        if (activationCode.length() < 6) {
            return false;
        }
        if (s.startsWith(activationCode)) {
            SPManage.set(UtilsConfig.activationCode, msg);
            try {
                writeRegister(getInitJson());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static String stringToMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    private static String getInitJson() {
        String appId = SPManage.get(UtilsConfig.appId);
        String activationCode = SPManage.get(UtilsConfig.activationCode);
        if (TextUtils.isEmpty(appId)) {
            appId = TimeUtils.getCurrentTimeFor2() + UUID.randomUUID().toString();
            SPManage.set(UtilsConfig.appId, appId);
        }
        Map<String, String> map = new HashMap<>();
        map.put(key_id, appId);
        map.put(key_code, activationCode);

        String s1 = new Gson().toJson(map);
        return s1;
    }


    public static final String key_id = "id";
    public static final String key_code = "code";

    public static String getAPPid(Context context) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("appName", context.getString(R.string.app_name));
        map.put("appVersionCode", TDevice.getVersionCode(context) + "");
        map.put("appVersionName", TDevice.getVersionName(context));
        map.put("MD5", APKMsgUtils.getAppMD5(context));
        map.put("appId", UUID.randomUUID().toString());
        return "";
    }

    /**
     * @param data   加密后的数据
     * @param entkey 加密后的密钥
     * @return
     */
    public static String toJson(String data, String entkey) {
        Map<String, String> map = new HashMap<>();
        map.put("data", entkey);
        map.put("entkey", entkey);
        map.put("entType", "1");
        String s = new Gson().toJson(map);
        return s;
    }
}
