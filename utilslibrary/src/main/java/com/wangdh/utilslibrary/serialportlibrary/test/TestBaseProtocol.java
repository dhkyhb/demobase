package com.wangdh.utilslibrary.serialportlibrary.test;

import com.wangdh.utilslibrary.exception.AppErrorCode;
import com.wangdh.utilslibrary.exception.AppException;
import com.wangdh.utilslibrary.utils.Wbyte;
import com.wangdh.utilslibrary.utils.TLog;

public abstract class TestBaseProtocol extends StandardProtocol {
    //独有属性
    public boolean isBack;

    @Override
    public void checkProtoclo(byte[] data) throws AppException {
        TLog.e("解密："+Wbyte.bcdToString(data));
        check(data, isBack);
    }

    @Override
    public byte[] getProtocol() {
        try {
            byte[] bytes = dataPack(data);
            bytes = encryption(bytes);
            TLog.e("发送数据:" + Wbyte.bcdToString(bytes));
            beginSend();
            return bytes;
        } catch (AppException e) {
            e.printStackTrace();
            failed(data, e);
        } catch (Exception e) {
            e.printStackTrace();
            failed(data, new AppException(e.getMessage()));
        }
        return null;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>如果大多数主板协议的校验功能是一样的 应该抽取到工具类中
    //加密
    public static byte[] encryption(byte[] msg) {
        //获取crc校验码
        int crc = crc16(msg, msg.length);
        byte[] to16byte = Wbyte.to16byte(crc, 2);
        //反转校验码 ， 高低转换成低高
        byte[] back = back(to16byte);
        //拼接  +crc校验码+FE
        byte[] bytes = Wbyte.addBytes(msg, back, new byte[]{(byte) 0xFE});
        return bytes;
    }

    private static void check(byte[] msg, boolean isBack) throws AppException {
        try {
            byte[] l = Wbyte.subLeft(msg, 1);
            byte[] r = Wbyte.subRight(msg, 1);

            if (l[0] != (byte) 0XFF) {
                throw new Exception();
            }
            if (r[0] != (byte) 0XFE) {
                throw new Exception();
            }

        } catch (Exception e) {
            throw new AppException(AppErrorCode.SERIAL_CHECK_BIT_ERROR);
        }
        try {
            byte[] sub = Wbyte.sub(msg, 1, 2);
            int i = Wbyte.toIntFor16(sub);
            if (msg.length != i) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new AppException(AppErrorCode.SERIAL_LEN_ERROR);
        }

        try {
            byte[] sub = Wbyte.sub(msg, 1, 2);
            int i = Wbyte.toIntFor16(sub);
            if (msg.length == 6 && i == 6) {
                byte[] errorCode = Wbyte.sub(msg, 4, 5);
                throw new AppException(Wbyte.bcdToString(errorCode));
            }
        } catch (AppException app) {
            throw app;
        } catch (Exception e) {
            throw new AppException(AppErrorCode.SERIAL_LEN_ERROR);
        }

        try {
            byte[] sub = Wbyte.sub(msg, 0, msg.length - 3);
            int crc = crc16(sub, sub.length);
            byte[] to16byte = Wbyte.to16byte(crc, 2);

            if (!isBack) {
                //反转校验码 ， 高低转换成低高
                to16byte = back(to16byte);
            }
            byte[] c1 = Wbyte.sub(msg, msg.length - 3, msg.length - 2);
            byte[] c2 = Wbyte.sub(msg, msg.length - 2, msg.length - 1);
            if (c1[0] != to16byte[0]) {
                throw new Exception();
            }
            if (c2[0] != to16byte[1]) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new AppException(AppErrorCode.SERIAL_CRC);
        }
    }

    private static byte[] back(byte[] b) {
        byte[] res = new byte[2];
        System.arraycopy(b, 1, res, 0, 1);
        System.arraycopy(b, 0, res, 1, 1);
        Wbyte.log(res);
        return res;
    }

    public static int crc16(byte[] buf, int Len) {
        int IX, IY, CRC16;
        CRC16 = 0xFFFF;

        if (Len <= 0) return 0;

        for (IX = 0; IX < Len; IX++) {
            int i = Wbyte.toIntFor16(buf[IX]);
            CRC16 = CRC16 ^ i;
            for (IY = 0; IY < 8; IY++) {
                if ((CRC16 & 1) != 0)
                    CRC16 = (CRC16 >> 1) ^ 0xA001;
                else
                    CRC16 = CRC16 >> 1;
            }
        }
        return CRC16;
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
}
