package com.wangdh.demolist.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.wangdh.demolist.R;
import com.wangdh.utilslibrary.exception.AppException;
import com.wangdh.utilslibrary.serialportlibrary.Device;
import com.wangdh.utilslibrary.serialportlibrary.SerialPort;
import com.wangdh.utilslibrary.serialportlibrary.SerialPortFinder;
import com.wangdh.utilslibrary.serialportlibrary.listener.LogcatListener;
import com.wangdh.utilslibrary.serialportlibrary.listener.SerialReadListener;
import com.wangdh.utilslibrary.utils.BCDHelper;
import com.wangdh.utilslibrary.utils.Wbyte;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SerialPortTestActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText btl;
    private EditText sendData;
    private TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_port_test);
        spinner = findViewById(R.id.spinner);
        btl = findViewById(R.id.btl);
        sendData = findViewById(R.id.sendData);
        log = findViewById(R.id.log);

        sendData.setText("EC07FFFE0100C1B4");
        // Example of a call to a native method
//        TextView tv = findViewById(R.id.sample_text);
//        tv.setText(new SerialPort().stringFromJNI());
        initPort();
    }

    private void setLog(final String msg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String s = log.getText().toString();
                log.setText(msg + "\n" + s);
            }
        });

    }

    private ArrayList<Device> devices;
    private int select = 1;

    private void initPort() {
        SerialPortFinder serialPortFinder = new SerialPortFinder();
        devices = serialPortFinder.getDevices();
        if (devices == null || devices.size() <= 0) {
            return;
        }
        String[] key = new String[devices.size()];
        for (int i = 0; i < devices.size(); i++) {
            key[i] = devices.get(i).getName();
        }
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, key));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    SerialPort serialPort;

    public void openSP(View view) {
        File file = new File("/proc/tty/drivers");
        boolean canRead = file.canRead();
        Log.e("www", "是否可以读写 = " + canRead);

        Device device = devices.get(select);
        Log.e("wdh", "选择串口：" + device.getName());
        String b = btl.getText().toString().trim();
        if (serialPort != null) {
            close(view);
        }
        if (serialPort == null) {
            serialPort = new SerialPort();
            serialPort.setReadListener(new SerialReadListener() {
                @Override
                public void read(byte[] msg) {
                    setLog("返回：" + Wbyte.bcdToString(msg));
                }

                @Override
                public void error(AppException e) {

                }
            });
            serialPort.setLogcatListen(new LogcatListener() {
                @Override
                public void rev(String log) {
                    setLog(log);
                }
            });
            try {
                serialPort.open(device.getFile(), b);
                setLog("串口打开成功");
                readThread();
            } catch (Exception e) {
                e.printStackTrace();
                serialPort = null;
                setLog("串口打开失败");
            }
        }
    }

    private void readThread() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                byte[] mReadBuffer = new byte[1024];
                while (!isInterrupted()) {
                    int size = 0;
                    try {
                        mReadBuffer = new byte[1024];
                        size = serialPort.mFileInputStream.read(mReadBuffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (-1 == size || 0 >= size) {
//                        setLog("数据为null");
                        continue;
                    }
                    byte[] readBytes = new byte[size];
                    System.arraycopy(mReadBuffer, 0, readBytes, 0, size);
                    setLog("读取到了" + new String(readBytes));
                }
            }
        };
    }

    public void send(View view) {
        if (serialPort == null) {
            setLog("串口未打开");
            return;
        }
        String d = sendData.getText().toString().trim();
        byte[] bytes = Wbyte.stringToBcd(d);
        serialPort.send(bytes);
    }


    public void read(View view) {
        if (serialPort == null) {
            setLog("串口未打开");
            return;
        }
        byte[] response = serialPort.response();
//        byte[] mReadBuffer = new byte[1024];
//        int size = 0;
//        try {
//            size = serialPort.mFileInputStream.read(mReadBuffer);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        setLog("size" + size);
//        if (-1 == size || 0 >= size) {
//            return;
//        }
//        byte[] readBytes = new byte[size];
//        System.arraycopy(mReadBuffer, 0, readBytes, 0, size);
//        setLog("读取到了" + new String(readBytes));
    }

    public void close(View view) {
        if (serialPort == null) {
            setLog("未发现打开的串口");
            return;
        }
        serialPort.close();
        serialPort = null;
    }



}
