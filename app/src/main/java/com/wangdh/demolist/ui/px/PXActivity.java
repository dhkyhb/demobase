package com.wangdh.demolist.ui.px;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wangdh.demolist.R;
import com.wangdh.demolist.service.PService;
import com.wangdh.utilslibrary.rx.RXTest;


/**
 * 测试  hdip  mdip ldip 图片的
 */
public class PXActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_px);
//        RXTest.tt();
    }

    public void click(View view) {
        Intent intent = new Intent(this, PService.class);
        startService(intent);
    }
}
