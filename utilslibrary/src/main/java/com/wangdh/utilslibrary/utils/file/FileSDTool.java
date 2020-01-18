package com.wangdh.utilslibrary.utils.file;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * @author wangdh
 * @time 2019/8/5 16:49
 * @describe 针对sd卡的文件操作  不推荐用static  毕竟耗时 多线程问题
 */
public class FileSDTool {

    public static String getSDPath() {
        String path = Environment
                .getExternalStorageDirectory()
                + File.separator + "aaawdh" + File.separator;
//                + "adservice"
//                + File.separator + "download" + File.separator;
        return path;
    }

    public static String getSDAbsolutePath() {
        String path = Environment
                .getExternalStorageDirectory().getAbsolutePath();
        return path;
    }

    /**
     * 将文件写入本地
     *
     * @param destFileDir  目标文件夹
     * @param destFileName 目标文件名
     * @return 写入完成的文件
     * @throws IOException IO异常
     */
    private File saveFile(InputStream is, String destFileDir, String destFileName) throws IOException {
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return file;

        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //多功能删除
    public boolean delete(String path, String name) {
        String fileName = path + name;
        try {
            File file = new File(path + name);
            if (!file.exists()) {
                return true;
            } else {
                if (file.isFile()) {
                    deleteFile(fileName);
                } else {
                    deleteDirectory(fileName);
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return true;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }


    /**
     * 将字符串写入到文本文件中
     *
     * @param strcontent 文本
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public void writeTxtToFile(StringBuffer strcontent, String filePath, String fileName) throws Exception {
        makeFilePath(filePath, fileName);

        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        strcontent.append("\r\n");
        File file = new File(strFilePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
        raf.seek(file.length());
        raf.write(strcontent.toString().getBytes());
        raf.close();
    }

    // 生成文件
    public File makeFilePath(String filePath, String fileName) throws IOException {
        File file = null;
        makeRootDirectory(filePath);
        file = new File(filePath + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    // 生成文件夹
    public void makeRootDirectory(String filePath) {
        File file = null;
        file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
