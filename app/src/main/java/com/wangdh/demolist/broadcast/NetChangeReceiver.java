package com.wangdh.demolist.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.wangdh.utilslibrary.utils.logger.TLog;


public class NetChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                //有网处理
                TLog.e("有网处理");
            } else {
                //无网显示个提示什么的
                TLog.e("无网处理");
                Toast.makeText(context, "无网络，请检测网络状态！", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            //ignore
        }
    }
}
