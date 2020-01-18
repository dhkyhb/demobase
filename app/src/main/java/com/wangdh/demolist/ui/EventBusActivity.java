package com.wangdh.demolist.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wangdh.demolist.R;
import com.wangdh.utilslibrary.utils.TLog;
import com.wangdh.utilslibrary.utils.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class EventBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
    }
    List<EventBus> eventBuss = new ArrayList<>();
    public void test1(View view) {
        for (int i = 0; i < 1000; i++) {
            EventBus eventBus = new EventBus();
            eventBus.register(this);
            eventBuss.add(eventBus);
        }

    }

    public void test2(View view) {
        for (EventBus buss : eventBuss) {
            buss.unregister(this);
            buss.removeAllStickyEvents();
        }
        eventBuss.removeAll(eventBuss);
    }

    public void test3(View view) {
        for (EventBus buss : eventBuss) {
            buss.post(TimeUtils.getCurrentTimeFor2());
        }

    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void get(String msg){
        TLog.e(msg);
    }
}
