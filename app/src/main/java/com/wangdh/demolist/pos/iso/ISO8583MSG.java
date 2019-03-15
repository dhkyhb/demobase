package com.wangdh.demolist.pos.iso;

import android.text.TextUtils;

import com.wangdh.demolist.utils.sort.QuickIsoConfig;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangdh on 2018/5/29.
 */

public class ISO8583MSG {
    public static String packet_encoding = "UTF-8";//报文编码 UTF-8 GBK

    Map<String, String> valueMap = new HashMap<>();

    Map<String, ISO8583Config> configsMap = new HashMap<>();
    Map<String, ISO8583Config> isoConfig = new LinkedHashMap<>();

    public ISO8583MSG() {
    }

    public ISO8583MSG(Class c) throws Exception {
        Field[] fields = c.getFields();
        List<ISO8583Config> configs = new ArrayList<>();

        for (Field field : fields) {
            ISO8583Config annotation = field.getAnnotation(ISO8583Config.class);
            if (annotation != null) {
                configs.add(annotation);
                configsMap.put(annotation.name(), annotation);
            }
        }
        ISO8583Config[] con = configs.toArray(new ISO8583Config[configs.size()]);
        new QuickIsoConfig().sort(con);
        for (ISO8583Config co : con) {
            isoConfig.put(co.name(), co);
        }
    }

    //String v:为utf8
    public void add(String key, String v) {
        if (TextUtils.isEmpty(v)) {
            return;
        }
        valueMap.put(key, v);
    }

    //byte[] v:为Ascii
    public void add(String key, byte[] v) {
        if (v == null) {
            return;
        }
        try {
            valueMap.put(key, new String(v, packet_encoding));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] pack() throws Exception {
        Set<String> keys = this.isoConfig.keySet();
        for (String key : keys) {
            ISO8583Config iso8583Config = isoConfig.get(key);
            String name = iso8583Config.name();

            String fieldValue = this.valueMap.get(name);
            if (fieldValue == null) {
                continue;
            }

            String _bodyType = iso8583Config.bodyType();
            int _bodyLen = iso8583Config.bodyLen();

            String _lenType = iso8583Config.lenType();
            int _lenLenght = iso8583Config.lenLenght();
            int _lenRadix = iso8583Config.lenRadix();

            String _completion = iso8583Config.completion();
            String _align = iso8583Config.align();

            //将域值编码转换，保证报文编码统一
            fieldValue = new String(fieldValue.getBytes(packet_encoding), packet_encoding);
            boolean isFixLen = true;//是否定长判断
            if (_lenLenght <= 0) {
                isFixLen = false;
            }
            int bodyLen = 0;
            if (_bodyType.equalsIgnoreCase("BCD")) {
                bodyLen = IsoUtils.stringToBcd(fieldValue).length;
            } else if (_bodyType.equalsIgnoreCase("ASCII")) {
                byte[] bytes = new String(fieldValue).getBytes();
                fieldValue = IsoUtils.bcdToString(bytes);
                bodyLen = bytes.length;
            } else if (_bodyType.equalsIgnoreCase("BINARY")) {
                bodyLen = IsoUtils.stringToBcd(fieldValue).length * 2;
            }

            if (isFixLen) {
                fieldValue = IsoUtils.complement(fieldValue, _completion, _bodyLen, _align.equalsIgnoreCase("left"));
            } else {
                String strLen = "";
                //如果不是定长
                int length = fieldValue.length();
                if (_lenLenght % 2 != 0) {
                    _lenLenght = _lenLenght + (_lenLenght % 2);
                }
                String ll = String.format("%0" + _lenLenght + "d", length);
                if (_lenType.equalsIgnoreCase("BCD")) {
                    //长度为字符长
                } else if (_lenType.equalsIgnoreCase("ASCII")) {
                    //长度为字节长
                    byte[] bytes = new String(ll).getBytes();
                    ll = IsoUtils.bcdToString(bytes);
                } else if (_lenType.equalsIgnoreCase("BINARY")) {
                    //长度类型无此选项
                }

            }
        }
        return null;
    }

    public void unpack(byte[] msg) throws Exception {

    }

}
