package com.wangdh.utilslibrary.utils;

import java.io.ByteArrayOutputStream;

/**
 * Created by wangdh on 2018/5/12.
 * byte 工具类
 */

public class Wbyte {
    public static String decimalToHex(int decimal) {
        String hex = "";
        while (decimal != 0) {
            int hexValue = decimal % 16;
            hex = toHexChar(hexValue) + hex;
            decimal = decimal / 16;
        }
        return hex;
    }

    //将0~15的十进制数转换成0~F的十六进制数
    public static char toHexChar(int hexValue) {
        if (hexValue <= 9 && hexValue >= 0)
            return (char) (hexValue + '0');
        else
            return (char) (hexValue - 10 + 'A');
    }

    public static String left0(String msg, int len) {
        if (msg.length() >= len) {
            return msg;
        }
        int num = len - msg.length();
        for (int i = 0; i < num; i++) {
            msg = "0" + msg;
        }
        return msg;
    }

    //左补零 双数
    public static String left0(String msg) {
        if (msg.length() % 2 == 0) {
            return msg;
        }
        msg = "0" + msg;
        return msg;
    }

    //添加16进制 2字节长度
    public static byte[] add16Len(byte[] msg) {
        int length = msg.length;
        String s = decimalToHex(length);
        String s1 = left0(s, 4);
        byte[] bytes = stringToBcd(s1);
        byte[] sucess = new byte[msg.length + 2];
        System.arraycopy(bytes, 0, sucess, 0, 2);
        System.arraycopy(msg, 0, sucess, 2, msg.length);
        return sucess;
    }

    public static byte[] add10Len(byte[] msg) {
        int length = msg.length;
        String format = String.format("%04d", length);
        byte[] bytes = stringToBcd(format);
        byte[] sucess = new byte[msg.length + 2];
        System.arraycopy(bytes, 0, sucess, 0, 2);
        System.arraycopy(msg, 0, sucess, 2, msg.length);
        return sucess;
    }

    //把表示16进制的byte 转换成10进制数字
    public static int toIntFor16(byte[] msg) {
        String s = bcdToString(msg);
        Integer x = Integer.parseInt(s, 16);
        return x.intValue();
    }
    public static int toIntFor16(byte msg) {
        String s = BCDHelper.bcdToString(new byte[] {msg});
        Integer x = Integer.parseInt(s, 16);
        return x.intValue();
    }
    //把表示10进制的byte 转换成10进制数字
    public static int toIntFor10(byte[] msg) {
        String s = bcdToString(msg);
        Integer x = Integer.parseInt(s, 10);
        return x.intValue();
    }

    /**
     * 数字转 16进制
     *
     * @param num
     * @param len byte 长度
     * @return
     */
    public static byte[] to16byte(int num, int len) {
        String s = decimalToHex(num);
        s = left0(s);
        byte[] bytes = stringToBcd(s);
        if (len <= 0 || bytes.length - len >= 0) {
            return bytes;
        }
        int i = len - bytes.length;
        byte[] left = new byte[i];
        byte[] _new = new byte[len];
        System.arraycopy(left, 0, _new, 0, i);
        System.arraycopy(bytes, 0, _new, i, len - i);
        return _new;
    }

    public static byte[] to10byte(int num, int len) {
        String s = String.valueOf(num);
        s = left0(s);
        byte[] bytes = stringToBcd(s);
        if (len <= 0 || bytes.length - len >= 0) {
            return bytes;
        }
        int i = len - bytes.length;
        byte[] left = new byte[i];
        byte[] _new = new byte[len];
        System.arraycopy(left, 0, _new, 0, i);
        System.arraycopy(bytes, 0, _new, i, len - i);
        return _new;
    }

    public static byte[] subLeft(byte[] b, int i) {
        byte[] bytes = new byte[i];
        System.arraycopy(b, 0, bytes, 0, i);
        return bytes;
    }

    public static byte[] subRight(byte[] b, int i) {
        byte[] bytes = new byte[i];
        System.arraycopy(b, b.length - i, bytes, 0, i);
        return bytes;
    }

    public static byte[] sub(byte[] b, int start, int end) {
        byte[] bytes = new byte[end - start];
        System.arraycopy(b, start, bytes, 0, end - start);
        return bytes;
    }

    public static void log(byte[] msg) {
        if (msg == null) {
            System.out.println("null");
            return;
        }
        if (msg.length <= 0) {
            System.out.println("");
            return;
        }
        System.out.print("Wbyte:");
        for (int i = 0; i < msg.length; i++) {
            System.out.printf("%02X", msg[i]);
            if (i == msg.length - 1) {
                System.out.println("");
            }
        }
    }


    //出现单数
    public static byte[] addLeftByANSI(byte[] src, String msg) {
        byte[] bytes = stringToBcd(msg);
        byte[] _new = new byte[src.length + bytes.length];
        System.arraycopy(bytes, 0, _new, 0, bytes.length);
        System.arraycopy(src, 0, _new, bytes.length, src.length);
        return _new;
    }

    public static byte[] addRightByANSI(byte[] src, String msg) {
        byte[] bytes = stringToBcd(msg);
        byte[] _new = new byte[src.length + bytes.length];
        System.arraycopy(src, 0, _new, 0, src.length);
        System.arraycopy(bytes, 0, _new, src.length, bytes.length);
        return _new;
    }

    public static byte[] addBytes(byte[] v1, byte[]... v2) {
        byte[] all = new byte[0];
        byte[] befor = v1;

        for (byte[] bytes : v2) {
            int bl = bytes.length;
            all = new byte[befor.length + bl];
            System.arraycopy(befor, 0, all, 0, befor.length);
            System.arraycopy(bytes, 0, all, befor.length, bytes.length);

            befor = new byte[all.length];
            System.arraycopy(all, 0, befor, 0, all.length);
        }
        return all;
    }

    //适用于 多数据(Object 多)添加 效率高
    public static byte[] addBytes(byte[] v1, Object... array) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write(v1);
        for (Object bytes : array) {
            output.write((byte[]) bytes);
        }
        return output.toByteArray();
    }

    //转2进制字符串
    public static String to2BinaryStringByByte(byte[] msg) {
        String s = "";
        for (int i = 0; i < msg.length; i++) {
            s += Integer.toBinaryString((msg[i] & 0xFF) + 0x100).substring(1);
        }
        return s;
    }

    public static String fromANSI(String msg) {
        byte[] bytes = stringToBcd(msg);
        try {
            return new String(bytes, "GBK").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String bcdToString(byte[] bcdNum) {
        StringBuffer sb = new StringBuffer();
        try {
            int len = bcdNum.length;
            for (int i = 0; i < len; i++) {
                sb.append(Integer.toHexString((bcdNum[i] & 0xF0) >> 4));
                sb.append(Integer.toHexString(bcdNum[i] & 0x0F));
            }
        } catch (Exception e) {
        }
        return sb.toString().toUpperCase();
    }

    public static byte[] stringToBcd(String src) {
        int inum = 0;
        int numlen = src.length();
        if ((numlen % 2) > 0) return null;
        byte[] dst = new byte[numlen / 2];

        for (int i = 0; i < numlen; ) {
            //TODO: 过滤空格
            char hghch = ConvertHexChar(src.charAt(i));
            char lowch = ConvertHexChar(src.charAt(i + 1));

            dst[inum++] = (byte) (hghch * 16 + lowch);
            i += 2;
        }
        return dst;
    }

    private static char ConvertHexChar(char ch) {
        if ((ch >= '0') && (ch <= '9'))
            return (char) (ch - 0x30);
        else if ((ch >= 'A') && (ch <= 'F'))
            return (char) (ch - 'A' + 10);
        else if ((ch >= 'a') && (ch <= 'f'))
            return (char) (ch - 'a' + 10);
        else
            return (char) (-1);
    }

    public static byte[] StrToBCD(String str) {
        return StrToBCD(str, str.length());
    }


    public static byte[] StrToBCD(String str, int numlen) {
        if (numlen % 2 != 0)
            numlen++;

        while (str.length() < numlen) {
            str = "0" + str;  //前导补0
        }

        byte[] bStr = new byte[str.length() / 2];
        char[] cs = str.toCharArray();
        int i = 0;
        int iNum = 0;
        for (i = 0; i < cs.length; i += 2) {
            //TODO: 过滤空格
            int iTemp = 0;
            if (cs[i] >= '0' && cs[i] <= '9') {
                iTemp = (cs[i] - '0') << 4;
            } else {
                //  判断是否为a~f
                if (cs[i] >= 'a' && cs[i] <= 'f') {
                    cs[i] -= 32;
                }
                iTemp = (cs[i] - '0' - 7) << 4;
            }
            //  处理低位
            if (cs[i + 1] >= '0' && cs[i + 1] <= '9') {
                iTemp += cs[i + 1] - '0';
            } else {
                //  判断是否为a~f
                if (cs[i + 1] >= 'a' && cs[i + 1] <= 'f') {
                    cs[i + 1] -= 32;
                }
                iTemp += cs[i + 1] - '0' - 7;
            }
            bStr[iNum] = (byte) iTemp;
            iNum++;
        }
        return bStr;
    }

}
