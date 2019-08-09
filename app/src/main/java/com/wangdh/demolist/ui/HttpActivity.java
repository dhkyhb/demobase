package com.wangdh.demolist.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wangdh.demolist.R;
import com.wangdh.utilslibrary.netlibrary.NetTest;

public class HttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

    }

    public void test1(View view) {
//        DialogTip.getLoading(this).show();
        NetTest.conn_xiao_hua(this);
    }

}
