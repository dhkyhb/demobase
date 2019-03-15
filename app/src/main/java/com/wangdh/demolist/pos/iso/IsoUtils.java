package com.wangdh.demolist.pos.iso;

/**
 * Created by wangdh on 2018/5/29.
 */

public class IsoUtils {
    // 8583报文初始位图:128位01字符串
    public static String getInitBitMap() {
        String initBitMap =
                "10000000" + "00000000" + "00000000" + "00000000"
                        + "00000000" + "00000000" + "00000000" + "00000000"
                        + "00000000" + "00000000" + "00000000" + "00000000"
                        + "00000000" + "00000000" + "00000000" + "00000000";
        return initBitMap;
    }

    public static void quickSort(int a[], int low, int height) {
        if (low < height) {
            int result = partition(a, low, height);
            quickSort(a, low, result - 1);
            quickSort(a, result + 1, height);
        }
    }

    public static int partition(int a[], int low, int height) {
        int key = a[low];
        while (low < height) {
            while (low < height && a[height] >= key)
                height--;
            a[low] = a[height];
            while (low < height && a[low] <= key)
                low++;
            a[height] = a[low];
        }
        a[low] = key;
        return low;
    }

    public static char[] asciiToBcd(String src) {
        int inum = 0;
        String str = src.trim().replaceAll(" ", "");
        int numlen = str.length();
        if ((numlen % 2) > 0) return null;
        char[] dst = new char[numlen / 2];

        for (int i = 0; i < numlen; ) {
            //TODO: 过滤空格
            char hghch = ConvertHexChar(str.charAt(i));
            char lowch = ConvertHexChar(str.charAt(i + 1));

            dst[inum++] = (char) (hghch * 16 + lowch);
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

    public static byte[] stringToBcd(String src) {
        int inum = 0;
        int numlen = src.length();
        if ((numlen % 2) > 0) return null;
        byte[] dst = new byte[numlen / 2];

        for (int i = 0; i < numlen; ) {
            char hghch = ConvertHexChar(src.charAt(i));
            char lowch = ConvertHexChar(src.charAt(i + 1));

            dst[inum++] = (byte) (hghch * 16 + lowch);
            i += 2;
        }
        return dst;
    }

    public static String bcdToString(byte[] bcdNum) {
        int len = bcdNum.length;

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append(Integer.toHexString((bcdNum[i] & 0xF0) >> 4));
            sb.append(Integer.toHexString(bcdNum[i] & 0x0F));
        }
        return sb.toString().toUpperCase();
    }

    public static String complement(String src, String msg, int len, boolean isLeft) throws Exception {
        int length = src.length();
        if (length == len) {
            return src;
        }
        if (length > len) {
            throw new Exception("长度不符合要求");
        }
        if (length < len) {
            StringBuffer sb = new StringBuffer(src);
            for (int i = 0; i < (len - sb.length()); i++) {
                if (isLeft) {
                    sb.insert(0, msg);
                } else {
                    sb.append(msg);
                }
            }
            return sb.toString();
        }
        return src;
    }

    //将字符串转换成二进制字符串，以空格相隔
    public String StrToBinstr(String str) {
        char[] strChar = str.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            result += Integer.toBinaryString(strChar[i]);
        }
        return result;
    }
}
