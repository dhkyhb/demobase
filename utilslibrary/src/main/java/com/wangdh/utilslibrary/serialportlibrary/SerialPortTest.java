package com.wangdh.utilslibrary.serialportlibrary;

import com.kongqw.serialportlibrary.Device;
import com.kongqw.serialportlibrary.SerialPortFinder;
import com.kongqw.serialportlibrary.SerialPortManager;
import com.kongqw.serialportlibrary.listener.OnOpenSerialPortListener;
import com.kongqw.serialportlibrary.listener.OnSerialPortDataListener;
import com.wangdh.utilslibrary.utils.Wbyte;
import com.wangdh.utilslibrary.utils.logger.TLog;

import java.io.File;
import java.util.ArrayList;

/**
 * @author wangdh
 * @time 2019/8/12 15:27
 * @describe
 */
public class SerialPortTest {
    private SerialPortManager mSerialPortManager = null;

    public void test1() {
        mSerialPortManager = new SerialPortManager();
        mSerialPortManager.setOnOpenSerialPortListener(new OnOpenSerialPortListener() {
            @Override
            public void onSuccess(File file) {
                TLog.e("串口打开成功！" + file.getName());

            }

            @Override
            public void onFail(File file, Status status) {
                TLog.e("串口打开失败！");

            }
        });

        mSerialPortManager.setOnSerialPortDataListener(new OnSerialPortDataListener() {
            @Override
            public void onDataReceived(byte[] bytes) {
                TLog.e("串口接收数据2：" + Wbyte.bcdToString(bytes));
            }

            @Override
            public void onDataSent(byte[] bytes) {
                TLog.e("串口发送数据：" + Wbyte.bcdToString(bytes));

            }
        });
    }

    /**
     * 连接串口
     */
    private void conn(Device device) {
        boolean openSerialPort = false;
        try {
            openSerialPort = this.mSerialPortManager.openSerialPort(device.getFile(), 115200);
        } catch (Exception e) {
        }
        if (!openSerialPort) {
        }
    }

    //    this.mSerialPortManager.sendBytes(sendBytes);
//mSerialPortManager.closeSerialPort();
    public ArrayList<Device> scan() {
        SerialPortFinder serialPortFinder = new SerialPortFinder();
        ArrayList<Device> devices = serialPortFinder.getDevices();
        return devices;
    }
}
