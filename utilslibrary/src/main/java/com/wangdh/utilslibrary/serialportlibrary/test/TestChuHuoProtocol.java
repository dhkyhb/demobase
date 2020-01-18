package com.wangdh.utilslibrary.serialportlibrary.test;

import com.wangdh.utilslibrary.exception.AppErrorCode;
import com.wangdh.utilslibrary.exception.AppException;
import com.wangdh.utilslibrary.utils.Wbyte;

import java.util.Map;

public class TestChuHuoProtocol extends TestBaseProtocol {

    @Override
    public boolean matchingProtoclo() {
        byte[] bytes = Wbyte.sub(this.responseData, 3, 4);
        String s = Wbyte.bcdToString(bytes);
        //是弹簧机
        if (s.equalsIgnoreCase("A4")) {
            return true;
        } else {
//            throw new AppException(AppErrorCode.SERIAL_DATE_TYPE_ERROR);
        }
        return false;
    }

    //FF0C00A30B01010103E3AEFE
    @Override
    public byte[] dataPack(Map map) throws AppException {
        String h = (String) map.get("h");
        String l = (String) map.get("l");

        byte[] hh = Wbyte.to16byte(Integer.valueOf(h), 1);
        byte[] ll = Wbyte.to16byte(Integer.valueOf(l), 1);

        byte[] hl = Wbyte.addBytes(hh, ll);
        isBack=false;
        //A3 是弹簧(高低不反转)， A4 是履带（isBack=false;）
        String tou = " FF 0C 00 A4 0B".replace(" ", "");
        String wei = "01 03 ".replace(" ", "");

        byte[] to = Wbyte.StrToBCD(tou);
        byte[] we = Wbyte.StrToBCD(wei);

        byte[] bytes = Wbyte.addBytes(to, hl, we);
        return bytes;
    }

    @Override
    public void dataUnPack(byte[] msg) throws AppException {
        byte[] _11 = Wbyte.sub(msg, 10, 11);
        String s11 = Wbyte.bcdToString(_11);
        if (!s11.equalsIgnoreCase("A0")) {
            throw new AppException(AppErrorCode.SERIAL_NOT_RUN);
        }
    }
}
