package com.wangdh.utilslibrary.serialportlibrary.test;

import com.wangdh.utilslibrary.exception.AppException;
import com.wangdh.utilslibrary.serialportlibrary.SerialPort;
import com.wangdh.utilslibrary.serialportlibrary.SerialPortManager;
import com.wangdh.utilslibrary.serialportlibrary.listener.SerialProtocolListener;
import com.wangdh.utilslibrary.serialportlibrary.listener.SerialReadListener;
import com.wangdh.utilslibrary.serialportlibrary.protocol.ProtocolInterface;

import java.io.File;
import java.util.List;

public class Test {
    //第二步
    public void sendData(ProtocolInterface protocolInterface, SerialProtocolListener serialProtocolListener) {
        protocolInterface.setParentId(key);
        byte[] protocol = protocolInterface.getProtocol();
        //TODO 发送协议

    }

    private String key = "ttyUSB0串口的唯一id随便什么都可以";

    //第一步，打开串口 设置接收监听
    public void testSerial() {
        SerialPort serialPort = new SerialPort();
        boolean isOpen = serialPort.open(new File(""), "38400");
        if (isOpen) {
            serialPort.setReadListener(new SerialReadListener() {
                @Override
                public void read(byte[] msg) {
                    List<ProtocolInterface> pros = SerialPortManager.getListenerByKey(key);
                    for (ProtocolInterface protocolInterface : pros) {
                        boolean b = protocolInterface.responseProtocol(msg);
                        if (b) {
                            SerialPortManager.remove(key, protocolInterface);
                        }
                    }

                }

                @Override
                public void error(AppException e) {

                }
            });
        }
    }
}
