package com.wangdh.demolist.ui.adb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.wangdh.demolist.R;
import com.wangdh.utilslibrary.utils.logger.TLog;
import com.wangdh.utilslibrary.utils.root.CMD;

public class YuanChengActivity extends AppCompatActivity {
    private EditText port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuan_cheng);
        port = findViewById(R.id.port);
    }

    private String getPort() {
        String s = port.getText().toString();
        return s;
    }

    StringBuffer stringBuffer = new StringBuffer();
    private CMD cmd = new CMD().su();
    private CMD.CMDInterface cmdInterface = new CMD.CMDInterface() {
        @Override
        public void response(String msg) {
            TLog.e("read:" + msg);
//            stringBuffer.insert(0, msg + "\n");
//            TLog.e("ss:" + stringBuffer.toString());
        }
    };
    private int index = 0;

    public void startTCP(View view) {
        if (index == 0) {
            index++;
            cmd.setCmdInterface(cmdInterface);
        }
        try {
            cmd.send("setprop service.adb.tcp.port " + getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void padb(View view) {
        if (index == 0) {
            index++;
            cmd.setCmdInterface(cmdInterface);
        }
        try {
            cmd.send("stop adbd");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sadb(View view) {
        if (index == 0) {
            index++;
            cmd.setCmdInterface(cmdInterface);
        }
        try {
            cmd.send("start adbd");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cmd != null) {
            cmd.destroy();
        }
    }
}
