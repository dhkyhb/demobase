package com.wangdh.demolist.ui.px;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wangdh.dblibrary.DBTest;
import com.wangdh.demolist.R;
import com.wangdh.netlibrary.NetTest;


/**
 * 测试  hdip  mdip ldip 图片的
 */
public class PXActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_px);
        DBTest.insert();
//        NetTest._3(this);
        NetTest.conn_weather(this);
    }

    public void click(View view) {
        NetTest.conn_xiao_hua(this);
    }
}
