package com.wangdh.demolist.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wangdh.utilslibrary.utils.logger.TLog;

/**
 * 开机广播
 */
public class StartUpBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        TLog.e("开机启动广播 StartUpBroadcast");
//        boolean start = isStart(context);
//        if (!start) {
//            TLog.e("启动播放界面");
//            //启动守护进程
//            Intent mIntent = new Intent(context, WelcomeActivity.class);
//            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(mIntent);
//        } else {
//            TLog.e("播放界面已经在栈顶");
//        }
//        Intent service = new Intent(context, StartService.class);
//        service.putExtra("key", "广播");
//        context.startService(service);

    }


}
