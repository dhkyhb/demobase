package com.wangdh.demolist.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.kongqw.serialportlibrary.Device;
import com.kongqw.serialportlibrary.SerialPortFinder;
import com.kongqw.serialportlibrary.SerialPortManager;
import com.kongqw.serialportlibrary.listener.OnOpenSerialPortListener;
import com.kongqw.serialportlibrary.listener.OnSerialPortDataListener;
import com.wangdh.utilslibrary.utils.Wbyte;
import com.wangdh.utilslibrary.utils.logger.TLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 串口通讯服务
 * 使用服务是为了保持单个串口的 长时间连接，因为串口无法频繁 开启关闭。
 */
public class SerialPortService extends Service {
    private String portName;
    private String portNum;

    public SerialPortService() {
    }

    private List<SerialPortManager> listSerialPort = new ArrayList<>();


    private SerialPortManager mSerialPortManager = null;

    @Override
    public void onCreate() {
        TLog.e(this.getClass().getSimpleName() + "onCreate");
        super.onCreate();
        mSerialPortManager = new SerialPortManager();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TLog.e(this.getClass().getSimpleName() + "onStartCommand");
        if (intent != null) {
            portName = intent.getStringExtra("portName");
            portNum = intent.getStringExtra("portNum");
            com();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private boolean com() {
        mSerialPortManager.setOnOpenSerialPortListener(new OnOpenSerialPortListener() {
            @Override
            public void onSuccess(File file) {
                TLog.e("串口打开成功！" + file.getName());

            }

            @Override
            public void onFail(File file, Status status) {
                TLog.e("串口打开失败！" + status);

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
        ArrayList<Device> scan = scan();
        for (Device device : scan) {
            if (device.getName().equals(portName)) {
                boolean b = this.mSerialPortManager.openSerialPort(device.getFile(), Integer.valueOf(portNum).intValue());
                return b;
            }
        }
        return false;
    }

    public void send(String s) {
//        this.mSerialPortManager.sendBytes();
    }

    public boolean open(String name, String btl) {
        portName = name;
        portNum = btl + "";
        return com();
    }

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

    public ArrayList<Device> scan() {
        SerialPortFinder serialPortFinder = new SerialPortFinder();
        ArrayList<Device> devices = serialPortFinder.getDevices();
        return devices;
    }

    @Override
    public void onDestroy() {
        TLog.e(this.getClass().getSimpleName() + "onDestroy");
        super.onDestroy();
        try {
            this.mSerialPortManager.closeSerialPort();
        } catch (Exception e) {
        }
    }
}
