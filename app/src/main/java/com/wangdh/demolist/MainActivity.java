package com.wangdh.demolist;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.wangdh.demolist.ui.fragment.TextFragment;
import com.wangdh.demolist.ui.px.PXActivity;
import com.wangdh.utilslibrary.utils.BCDHelper;
import com.wangdh.utilslibrary.utils.logger.TLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fl_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        LibManager.init(this.getApplicationContext());
//        MainTest mainTest2 = new MainTest();
//        mainTest2.insert();

//        DaoManager2.init(getApplication());
//        DBTest mainTest = new DBTest();
//        mainTest.insert();
//        mainTest2.insert();

//        mainTest.test(this);
//        DaoManager.getSession().get
//        initView();

//        WebView viewById = (WebView) findViewById(R.id.wv_web);
//        viewById.loadUrl("www.baidu.com");

//        TestMain.main();

        startActivity(new Intent(this, PXActivity.class));
//        startActivity(new Intent(this, UpAPPActivity.class));

//        UIHelper.start(this, DowloadTestActivity.class);

//        DisplayMetrics metric = new DisplayMetrics();
//        int densityDpi = metric.densityDpi;
//        TLog.e("densityDpi:" + densityDpi);
//
//        float density = TDevice.getDensity();
//        TLog.e("density:" + density);
//        SockeDemo.text();
//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    net();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//        web();

    }

//    private void web() {
//        WebView wv_web = (WebView) findViewById(R.id.wv);
//        WebSettings settings = wv_web.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setDomStorageEnabled(true);//支持DOM API
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setDefaultTextEncodingName("UTF-8");
////        settings.setDisplayZoomControls(false);
//        wv_web.loadUrl("file:///android_asset/web/index.html");
//    }

    public void net() throws Exception {
        String msg = "005B600003000060310031131208000020000000c0001600000131303030303135393838303231303031303231303136300011000000010030002953657175656e6365204e6f3136333135305358582d34433330343131390003303120";
        Log.e("11", "12131313");
        String path = "http://145.4.206.244:5000/mjc/webtrans/VPB_lb HTTP/1.1";
//        String path="http://www.baidu.com/";
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(10000);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "x-ISO-TPDU/x-auth");
        connection.setRequestProperty("Content-Length", "93");
        connection.setRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty("User-Agent", "Donjin Http 0.1");
        connection.setRequestProperty("Accept", "*/*");
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(BCDHelper.stringToBcd(msg));
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            InputStream inputStream = connection.getInputStream();
            Log.e("inputStream", "inputStream:" + convertStreamToString(inputStream));
        }
        Log.e("responseCode", "responseCode:" + responseCode);
    }

    public static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public void startTextView(String msg) {
        TextFragment textFragment = new TextFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", msg);
        textFragment.setArguments(bundle);
        getFragment().replace(R.id.fl_main, textFragment);
        getFragment().commit();
    }

    private void initView() {
        fl_main = (FrameLayout) findViewById(R.id.fl_main);
        int width = fl_main.getLayoutParams().width;
        TLog.e("width:" + width);
    }

    private FragmentTransaction fragmentTransaction;

    public FragmentTransaction getFragment() {
        if (fragmentTransaction == null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
        }
        return fragmentTransaction;
    }

    public static void main() {

    }
}
