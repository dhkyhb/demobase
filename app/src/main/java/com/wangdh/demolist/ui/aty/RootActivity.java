package com.wangdh.demolist.ui.aty;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wangdh.demolist.R;
import com.wangdh.demolist.service.PService;
import com.wangdh.utilslibrary.utils.TimeUtils;
import com.wangdh.utilslibrary.utils.logger.TLog;
import com.wangdh.utilslibrary.utils.root.Shell;

import java.util.List;

public class RootActivity extends AppCompatActivity {

    private EditText mEditText;
    private TextView logs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        TLog.e("RootActivity 开启");
        initView();
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                int init = SPManage.getInt(DemoConfig.init);
//                int i = 5;
//                if (init <= i) {
//                    SPManage.set(DemoConfig.init, ++i);
//                    String[] strings = new String[1];
//                    strings[5] = "";
//                }
//
//            }
//        }.start();
    }

    public void send(View view) {
        String s = mEditText.getText().toString();
        try {
            StringBuffer send = new Shell().send(s);
            logs.setText(TimeUtils.getSysTime() + ":" + send);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.edit_text);
        mEditText.setText("logcat -d");
        logs = (TextView) findViewById(R.id.logs);
    }

    public void sendservice(View view) {
        Intent intent = new Intent(this, PService.class);
        intent.putExtra("key", "wqert679[");
        startService(intent);
    }

    public void pr(View view) {
        ActivityManager manager = (ActivityManager) this.getSystemService(this.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = manager.getRunningAppProcesses();
        String s = "";
        for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
            int pid = runningAppProcess.pid;
            String processName = runningAppProcess.processName;
            s = s + processName + " " + pid + "\n";
//            if (android.os.Process.myPid() != pid) {
//                android.os.Process.killProcess(pid);
//            }
        }
        logs.setText(s);
    }

    public void kill(View view) {
        String s = mEditText.getText().toString();
        try {
            Integer integer = Integer.valueOf(s);
            android.os.Process.killProcess(integer.intValue());
        } catch (Exception e) {
            logs.setText(e.getMessage());
        }

    }
}
