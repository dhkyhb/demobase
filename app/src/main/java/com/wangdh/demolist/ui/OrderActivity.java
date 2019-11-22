package com.wangdh.demolist.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wangdh.demolist.R;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setTitle("账单详情");
    }
}
