package com.wangdh.demolist.net;

import android.content.Context;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by wangdh on 2016/11/18.
 * name：
 * 描述：
 */

public class MyTrustManager implements X509TrustManager {
    // 相关的 jks 文件及其密码定义
    private final static String TRUST_STORE = "D:/test_client_trust.jks";
    private final static String TRUST_STORE_PASSWORD = "Testpassw0rd";
    X509TrustManager xtm;

    public MyTrustManager(Context mContext) throws Exception {
        // 载入 jks 文件
        KeyStore ks = KeyStore.getInstance("JKS");
        final InputStream inputStream = mContext.getAssets().open("srca.cer");
        ks.load(inputStream, null);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
        tmf.init(ks);
        TrustManager[] tms = tmf.getTrustManagers();
        // 筛选出 X509 格式的信任证书
        for (int i = 0; i < tms.length; i++) {
            if (tms[i] instanceof X509TrustManager) {
                xtm = (X509TrustManager) tms[i];
                return;
            }
        }
    }

    //// 服务端检验客户端证书的接口
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    // 客户端检验服务端证书的接口
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            xtm.checkServerTrusted(chain, authType);
        } catch (CertificateException excep) {
            System.out.println(excep.getMessage());
            throw excep;
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
//        return new X509Certificate[0];

        //return xtm.getAcceptedIssuers();
        return null;
    }

}
