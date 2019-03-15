package com.wangdh.demolist.pos.iso;

/**
 * Created by wangdh on 2018/5/30.
 */

public class ISO8583Bitmap {
    //转2进制字符串
    public static String to2BinaryStringByByte(byte[] msg) {
        String s = "";
        for (int i = 0; i < msg.length; i++) {
            s += Integer.toBinaryString((msg[i] & 0xFF) + 0x100).substring(1);
        }
        return s;
    }
}
