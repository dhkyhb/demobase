package com.wangdh.demolist.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.KeyStore;
import java.security.KeyStoreException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by wangdh on 2016/11/18.
 * name：
 * 描述：
 */

public class Simple_SSLSocket {
    // 定义要连接的服务器名和端口号
    private static final int DEFAULT_PORT = 443;
    private static final String DEFAULT_HOST = "https://kyfw.12306.cn/otn/";

    public static void start() {
        SSLSocket socket = null;
        // 使用默认的方式获取工厂实例
        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try {
            // 连接服务端的端口，完成握手过程
            socket = (SSLSocket) sf.createSocket(DEFAULT_HOST, DEFAULT_PORT);
            socket.startHandshake();
            System.out.println("Connected to " + DEFAULT_HOST + ":" + DEFAULT_PORT + " !");
            // 从控制台输入要发送给服务端的文字
//            BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
            Writer writer = new OutputStreamWriter(socket.getOutputStream());
            // 可以反复向服务端发送消息
//            boolean done=false;
//            while (!done) {
//                System.out.print("Send Message: ");
//                String line=reader.readLine();
//                if (line!=null) {
//                    writer.write(line+"\n");
//                    writer.flush();
//                }else{
//                    done=true;
//                }
//            }
            socket.close();
        } catch (Exception e) {
            System.out.println("Connection failed: " + e);
            try {
                socket.close();
            } catch (IOException ioe) {
            }
            socket = null;
        }
    }

    public static void demo() throws Exception {
        KeyStore srca = KeyStore.getInstance("srca");
    }

    // 相关的 jks 文件及其密码定义
    private final static String CERT_STORE = "D:/test_server_cert.jks";
    private final static String CERT_STORE_PASSWORD = "Testpassw0rd";

    // SSLContext 指定证书库
    public static void zd() throws Exception {
        // 载入 jks 文件
        FileInputStream f_certStore = new FileInputStream(CERT_STORE);
        KeyStore ks = KeyStore.getInstance("jks");
        ks.load(f_certStore, CERT_STORE_PASSWORD.toCharArray());
        f_certStore.close();

        // 创建并初始化证书库工厂
        String alg = KeyManagerFactory.getDefaultAlgorithm();
        KeyManagerFactory kmFact = KeyManagerFactory.getInstance(alg);
        kmFact.init(ks, CERT_STORE_PASSWORD.toCharArray());

        KeyManager[] kms = kmFact.getKeyManagers();

        // 创建并初始化 SSLContext 实例
        SSLContext context = SSLContext.getInstance("SSL");
        context.init(kms, null, null);
        SSLServerSocketFactory ssf = (SSLServerSocketFactory) context.getServerSocketFactory();
        SSLSocketFactory socketFactory = context.getSocketFactory();

    }


    // 相关的 jks 文件及其密码定义
    private final static String TRUST_STORE = "D:/test_client_trust.jks";
    private final static String TRUST_STORE_PASSWORD = "Testpassw0rd";

    /// SSLContext 指定信任库
    public static void zdxrk() throws Exception {
        // 载入 jks 文件
        FileInputStream f_trustStore = new FileInputStream(TRUST_STORE);
        KeyStore ks = KeyStore.getInstance("jks");
        ks.load(f_trustStore, TRUST_STORE_PASSWORD.toCharArray());
        f_trustStore.close();

        // 创建并初始化信任库工厂
        String alg = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmFact = TrustManagerFactory.getInstance(alg);
        tmFact.init(ks);

        TrustManager[] tms = tmFact.getTrustManagers();

        // 创建并初始化 SSLContext 实例
        SSLContext context = SSLContext.getInstance("SSL");
        context.init(null, tms, null);
        SSLSocketFactory sf = context.getSocketFactory();
    }
}
