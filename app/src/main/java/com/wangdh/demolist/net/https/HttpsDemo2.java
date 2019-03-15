package com.wangdh.demolist.net.https;

import android.content.Context;

import com.wangdh.utilslibrary.utils.logger.TLog;

import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by Administrator on 2016/11/20.
 */

public class HttpsDemo2 {
    private static final int SERVER_PORT = 443;//端口号
    private static final String SERVER_IP = "218.206.176.146";//连接IP
    private static final String CLIENT_KET_PASSWORD = "123456";//私钥密码
    private static final String CLIENT_TRUST_PASSWORD = "123456";//信任证书密码
    private static final String CLIENT_AGREEMENT = "TLS";//使用协议
    private static final String CLIENT_KEY_MANAGER = "X509";//密钥管理器
    private static final String CLIENT_TRUST_MANAGER = "X509";//
    private static final String CLIENT_KEY_KEYSTORE = "BKS";//密库，这里用的是BouncyCastle密库
    private static final String CLIENT_TRUST_KEYSTORE = "BKS";//
    private static final String ENCONDING = "utf-8";//字符集
    private SSLSocket Client_sslSocket;

    public void init(Context context) {
        try {
            //取得SSL的SSLContext实例
            SSLContext sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
            //取得KeyManagerFactory和TrustManagerFactory的X509密钥管理器实例
            KeyManagerFactory keyManager = KeyManagerFactory.getInstance(CLIENT_KEY_MANAGER);
            TrustManagerFactory trustManager = TrustManagerFactory.getInstance(CLIENT_TRUST_MANAGER);
            //取得BKS密库实例
            KeyStore kks = KeyStore.getInstance(CLIENT_KEY_KEYSTORE);
            KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
            //加客户端载证书和私钥,通过读取资源文件的方式读取密钥和信任证书
//            kks.load(context
//                    .getResources()
//                    .openRawResource(R.),CLIENT_KET_PASSWORD.toCharArray());
//            tks.load(context
//                    .getResources()
//                    .openRawResource(R.drawable.lt_client),CLIENT_TRUST_PASSWORD.toCharArray());
            //初始化密钥管理器
            keyManager.init(kks, CLIENT_KET_PASSWORD.toCharArray());
            trustManager.init(tks);
            //初始化SSLContext
            sslContext.init(keyManager.getKeyManagers(), trustManager.getTrustManagers(), null);
            //生成SSLSocket
            Client_sslSocket = (SSLSocket) sslContext.getSocketFactory().createSocket(SERVER_IP, SERVER_PORT);
        } catch (Exception e) {
            TLog.e("MySSLSocket", e.getMessage());
        }
    }
}
