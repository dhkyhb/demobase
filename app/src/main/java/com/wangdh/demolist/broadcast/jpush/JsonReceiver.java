package com.wangdh.demolist.broadcast.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wangdh.demolist.service.StartAppService;
import com.wangdh.utilslibrary.utils.logger.TLog;

import cn.jpush.android.api.JPushInterface;

/**
 * @author wangdh
 * @time 2019/7/4 15:09
 * @describe
 */
public class JsonReceiver extends BroadcastReceiver {
    public static final String MESSAGE_RECEIVED_ACTION = "com.zhnc.watch.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            //cn.jpush.android.intent.NOTIFICATION_RECEIVED //普通广播
            //cn.jpush.android.intent.MESSAGE_RECEIVED //自定义广播

            TLog.e("接收到的广播：" + intent.getAction());
            Bundle bundle = intent.getExtras();
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                TLog.e("[MyReceiver] 接收 Registration Id : " + regId);
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                String string = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                TLog.e("收到了自定义消息。消息内容是：" + string);
                // 自定义消息不会展示在通知栏，完全要开发者写代码去处理

                Intent intent1 = new Intent(context, StartAppService.class);
                intent1.putExtra("key", string);
                context.startService(intent1);
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                TLog.e("收到了通知");
                // 在这里可以做些统计，或者做些其他工作
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                TLog.e("用户点击打开了通知");
                // 在这里可以自己写代码去定义用户点击后的行为
//                Intent i = new Intent(context, MainActivity.class);  //自定义打开的界面
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(i);
            } else {
                TLog.e("Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
        }

    }
}
