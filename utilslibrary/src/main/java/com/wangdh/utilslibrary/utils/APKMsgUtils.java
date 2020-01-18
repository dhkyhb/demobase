package com.wangdh.utilslibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class APKMsgUtils {

    public static String getAppMD5(Context context) {
        String AppMD5 = "";
        try {
            PackageManager packageManager = context.getPackageManager();
//请注意需要PackageManager.GET_SIGNATURES 这个flag
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
// X509证书，X.509是一种非常通用的证书格式
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory
                    .generateCertificate(new ByteArrayInputStream(sign.toByteArray()));
// md5
            MessageDigest md = MessageDigest.getInstance("MD5");
// 获得公钥
            byte[] b = md.digest(cert.getEncoded());
//key即为应用签名
            AppMD5 = byte2HexFormatted(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppMD5;
    }

    /**
     * 将获取到得编码进行16进制转换
     *
     * @param arr
     * @return
     */
    private static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1)
                h = "0" + h;
            if (l > 2)
                h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1))
                str.append(':');
        }
        return str.toString();
    }

    /**
     * 获得app 的sha1值 *
     *
     * @param context
     * @return
     */
    public static String getAppSignSha1(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
// X509证书，X.509是一种非常通用的证书格式
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(sign.toByteArray())); // md5
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] b = md.digest(cert.getEncoded());// 获得公钥
            return byte2HexFormatted(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
