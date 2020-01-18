package com.wangdh.demolist.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;

import com.mobile.mobilehardware.sdcard.SDCardHelper;
import com.mobile.mobilehardware.signal.SignalHelper;
import com.mobile.mobilehardware.uniqueid.PhoneIdHelper;
import com.wangdh.demolist.R;
import com.wangdh.demolist.ui.adb.YuanChengActivity;
import com.wangdh.demolist.ui.aty.RootActivity;
import com.wangdh.utilslibrary.utils.TLog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.UUID;


/**
 * 测试  hdip  mdip ldip 图片的
 */
public class PXActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_px);
        String s = UUID.randomUUID().toString();
        TLog.e(s);
    }

    public void click(View view) {
        startActivity(new Intent(this, RootActivity.class));
    }

    public void click2(View view) {
        startActivity(new Intent(this, HttpActivity.class));
//        FileToolTest.test(this);
    }

    public void click3(View view) {
        startActivity(new Intent(this, SerialPortTestActivity.class));
    }

    public void click4(View view) {
        startActivity(new Intent(this, ListActivity.class));
    }

    public void adbST(View view) {
        startActivity(new Intent(this, YuanChengActivity.class));
    }


    public void click5(View view) {
        startActivity(new Intent(this, EventBusActivity.class));
    }
}
