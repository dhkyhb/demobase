package com.wangdh.demolist.ui.px;

import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.wangdh.dblibrary.DBTest;
import com.wangdh.demolist.R;
import com.wangdh.netlibrary.test.NetTest;

/**
 * 测试  hdip  mdip ldip 图片的
 */
public class PXActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_px);
        DBTest.insert();
//        NetTest._3(this);
        NetTest._3(this);
    }

    public void click(View view) {
        NetTest._3(null);
    }
}
