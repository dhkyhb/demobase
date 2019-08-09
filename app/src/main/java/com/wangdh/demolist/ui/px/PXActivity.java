package com.wangdh.demolist.ui.px;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wangdh.demolist.R;
import com.wangdh.demolist.service.PService;
import com.wangdh.demolist.ui.aty.RootActivity;
import com.wangdh.utilslibrary.dblibrary.DBTest;
import com.wangdh.utilslibrary.netlibrary.NetTest;
import com.wangdh.utilslibrary.rx.RXTest;
import com.wangdh.utilslibrary.utils.file.FileToolTest;
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
        NetTest.conn_xiao_hua(this);
        startActivity(new Intent(this, RootActivity.class));
        String s = UUID.randomUUID().toString();
        TLog.e(s);
    }

    public void click(View view) {

    }

    public void click2(View view) {
        FileToolTest.test(this);
    }
}
