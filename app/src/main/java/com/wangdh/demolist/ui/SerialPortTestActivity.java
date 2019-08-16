package com.wangdh.demolist.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.kongqw.serialportlibrary.Device;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.wangdh.demolist.R;
import com.wangdh.demolist.service.SerialPortService;
import com.wangdh.utilslibrary.utils.logger.TLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SerialPortTestActivity extends AppCompatActivity {
    Button protlist;
    ArrayList<Device> scan;
    List<String> data = new ArrayList<>();
    SerialPortService.SerialPortBinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_port_test);
        protlist = findViewById(R.id.select_prot);
    }

    public void spt1(View view) {
        startService(new Intent(this, SerialPortService.class));
    }

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TLog.e("ComponentName:" + name);
            binder = (SerialPortService.SerialPortBinder) service;
            scan = binder.getservices().scan();
            TLog.e("扫描串口:" + scan.size());
//            protlist.setText(scan.get(0).getName());
            for (Device device : scan) {
                data.add(device.getName());
                TLog.e(device.getName());
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            TLog.e("ComponentName:" + name);
        }
    };

    public void spt2(View view) {
        Intent intent = new Intent(this, SerialPortService.class);
        bindService(intent, sc, BIND_AUTO_CREATE);
    }

    public void spt3(View view) {
        try {
            unbindService(sc);
        } catch (Exception e) {
        }
    }

    public void select_prot(View view) {
        initListPopupIfNeed();
        mListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
        mListPopup.setPreferredDirection(QMUIPopup.DIRECTION_TOP);
        mListPopup.show(view);
    }

    private QMUIListPopup mListPopup;

    private void initListPopupIfNeed() {
        if (mListPopup == null) {

//            String[] listItems = new String[]{
//                    "Item 1",
//                    "Item 2",
//                    "Item 3",
//                    "Item 4",
//                    "Item 5",
//            };
//            List<String> data = new ArrayList<>();
//
//            Collections.addAll(data, listItems);

            ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.simple_list_item, data);

            mListPopup = new QMUIListPopup(this, QMUIPopup.DIRECTION_NONE, adapter);
            mListPopup.create(QMUIDisplayHelper.dp2px(this, 250), QMUIDisplayHelper.dp2px(this, 200), new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    protlist.setText(data.get(i));
                    mListPopup.dismiss();
                }
            });
            mListPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                }
            });
        }
    }

    String[] btl = new String[]{"2400", "4800", "9600", "14400", "19200", "28800", "38400", "57600", "76800", "115200"};

    public void open_prot(View view) {
        for (String datum : data) {
            for (String s : btl) {
                boolean open = binder.getservices().open(datum.toString(), s);
                if (open) {
                    break;
                }
            }
        }


    }
}
