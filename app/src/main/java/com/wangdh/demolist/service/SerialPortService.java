package com.wangdh.demolist.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.wangdh.utilslibrary.serialportlibrary.Device;
import com.wangdh.utilslibrary.serialportlibrary.SerialPort;
import com.wangdh.utilslibrary.serialportlibrary.SerialPortFinder;
import com.wangdh.utilslibrary.utils.TLog;

import java.util.ArrayList;

/**
 * 串口通讯服务
 * 使用服务是为了保持单个串口的 长时间连接，因为串口无法频繁 开启关闭。
 */
public class SerialPortService extends Service {
    public String name = "ttyS4";//串口名字
    public String baudrate = "38400";//波特率

    public int dataBits = 8;//数据位，5 ~ 8  （默认8）
    public int stopBits = 1;//停止位，1 或 2  （默认 1）
    public int parity = 2;//奇偶校验，0 None（默认）； 1 Odd； 2 Even

    public SerialPortService() {

    }

    @Override
    public void onCreate() {
        TLog.e(this.getClass().getSimpleName() + "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TLog.e(this.getClass().getSimpleName() + "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    SerialPort serialPort;

    //初始化连接
    private boolean init() {
        if (serialPort == null) {
            serialPort = new SerialPort();
        }
        ArrayList<Device> devices = new SerialPortFinder().getDevices();
        return false;
    }

    public void send(String s) {
//        this.mSerialPortManager.sendBytes();
    }

//    public boolean open(String name, String btl) {
//        portName = name;
//        portNum = btl + "";
//        return com();
//    }

    @Override
    public IBinder onBind(Intent intent) {
        SerialPortBinder serialPortBinder = new SerialPortBinder();
        serialPortBinder.setService(this);
        return serialPortBinder;
    }

    public class SerialPortBinder extends Binder {
        private SerialPortService service;

        public void setService(SerialPortService sps) {
            this.service = sps;
        }

        public SerialPortService getservices() {
            return this.service;
        }
    }


    @Override
    public void onDestroy() {
        TLog.e(this.getClass().getSimpleName() + "onDestroy");
        super.onDestroy();

    }
}
