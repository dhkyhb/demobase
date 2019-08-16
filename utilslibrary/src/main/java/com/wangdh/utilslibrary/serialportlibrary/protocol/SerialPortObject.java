package com.wangdh.utilslibrary.serialportlibrary.protocol;

import android.util.Log;

import com.kongqw.serialportlibrary.SerialPort;
import com.kongqw.serialportlibrary.listener.OnOpenSerialPortListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author wangdh
 * @time 2019/8/13 15:19
 * @describe
 */
public class SerialPortObject extends SerialPort {
    private static final String TAG = SerialPortObject.class.getSimpleName();
    private String name;
    private String port;

//    public boolean openSerialPort(File device, int baudRate) {
//        Log.i(TAG, "openSerialPort: " + String.format("打开串口 %s  波特率 %s", device.getPath(), baudRate));
//        if (!device.canRead() || !device.canWrite()) {
//            boolean chmod777 = this.chmod777(device);
//            if (!chmod777) {
//                Log.i(TAG, "openSerialPort: 没有读写权限");
//                if (null != this.mOnOpenSerialPortListener) {
//                    this.mOnOpenSerialPortListener.onFail(device, OnOpenSerialPortListener.Status.NO_READ_WRITE_PERMISSION);
//                }
//
//                return false;
//            }
//        }
//
//        try {
//            this.mFd = this.open(device.getAbsolutePath(), baudRate, 0);
//            this.mFileInputStream = new FileInputStream(this.mFd);
//            this.mFileOutputStream = new FileOutputStream(this.mFd);
//            Log.i(TAG, "openSerialPort: 串口已经打开 " + this.mFd);
//            if (null != this.mOnOpenSerialPortListener) {
//                this.mOnOpenSerialPortListener.onSuccess(device);
//            }
//
//            this.startSendThread();
//            this.startReadThread();
//            return true;
//        } catch (Exception var4) {
//            var4.printStackTrace();
//            if (null != this.mOnOpenSerialPortListener) {
//                this.mOnOpenSerialPortListener.onFail(device, OnOpenSerialPortListener.Status.OPEN_FAIL);
//            }
//
//            return false;
//        }
//    }
}
