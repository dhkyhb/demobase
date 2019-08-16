package com.wangdh.demolist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wangdh.demolist.R;
import com.wangdh.demolist.ui.aty.RootActivity;
import com.wangdh.utilslibrary.utils.logger.TLog;

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
}
