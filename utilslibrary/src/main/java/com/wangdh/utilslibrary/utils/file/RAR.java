package com.wangdh.utilslibrary.utils.file;

import android.os.Build;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author wangdh
 * @time 2019/7/18 17:26
 * @describe 解压 出现乱码问题 修改 new ZipFile(zipName，"GBK"); 但是需要高等级 api 24
 */
public class RAR {
    private final static int BUFFER = 1024;

    public boolean unZip(String zipDirectory, String storageDirectory,
                         String fileName) {
        boolean result = false;
        String zipName = zipDirectory + fileName;
        String filePath = storageDirectory;
        ZipFile zipFile = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                zipFile = new ZipFile(zipName, Charset.forName("GBK"));
            }else{
                zipFile = new ZipFile(zipName);
            }
            Enumeration<? extends ZipEntry> emu = zipFile.entries();
            while (emu.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) emu.nextElement();
                if (entry.isDirectory()) {
                    new File(filePath + entry.getName()).mkdirs();
                    continue;
                }
                bis = new BufferedInputStream(zipFile.getInputStream(entry));
                File file = new File(filePath + entry.getName());
                File parent = file.getParentFile();
                if (parent != null && (!parent.exists())) {
                    parent.mkdirs();
                }
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos, BUFFER);
                int count;
                byte data[] = new byte[BUFFER];
                while ((count = bis.read(data, 0, BUFFER)) != -1) {
                    bos.write(data, 0, count);
                }
                bos.flush();
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (null != bos) {
                    bos.close();
                }
                if (null != bis) {
                    bis.close();
                }
                if (null != zipFile) {
                    zipFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                result = false;
            }
        }
        return result;
    }

}
