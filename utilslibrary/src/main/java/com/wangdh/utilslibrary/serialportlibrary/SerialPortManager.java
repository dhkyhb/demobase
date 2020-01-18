package com.wangdh.utilslibrary.serialportlibrary;

import com.wangdh.utilslibrary.serialportlibrary.protocol.ProtocolInterface;
import com.wangdh.utilslibrary.utils.TLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerialPortManager {
    public static Map<String, List<ProtocolInterface>> allProtocolInterface = new HashMap<>();

    public static void add(String key, ProtocolInterface ProtocolInterface) {
        TLog.e("添加：" + key + "  " + ProtocolInterface);
        List<ProtocolInterface> ProtocolInterfaces = allProtocolInterface.get(key);
        if (ProtocolInterfaces == null) {
            ProtocolInterfaces = new ArrayList<>();
            allProtocolInterface.put(key, ProtocolInterfaces);
        }
        ProtocolInterfaces.add(ProtocolInterface);
    }

    public static void remove(String key, ProtocolInterface ProtocolInterface) {
        TLog.e("删除：" + key + "  " + ProtocolInterface);
        List<ProtocolInterface> ProtocolInterfaces = allProtocolInterface.get(key);
        if (ProtocolInterfaces == null) {
            return;
        }
        ProtocolInterfaces.remove(ProtocolInterface);
    }

    public static List<ProtocolInterface> getListenerByKey(String key) {
        List<ProtocolInterface> ProtocolInterfaces = allProtocolInterface.get(key);
        return ProtocolInterfaces;
    }
}
