package com.wangdh.demolist.utils.updata;

import android.app.Activity;
import android.util.Log;

public class UpdataTest {
    public static final String TAG = "UpdataTest";

    public static AppDownloadManager test(Activity activity) {
        String url = "http://gdown.baidu.com/data/wisegame/fd84b7f6746f0b18/baiduyinyue_4802.apk";
        AppDownloadManager appDownloadManager = new AppDownloadManager(activity);
        appDownloadManager.setUpdateListener(new AppDownloadManager.OnUpdateListener() {
            @Override
            public void update(int currentByte, int totalByte) {
                Log.i(TAG, "监听：" + currentByte + "/" + totalByte + "");
            }
        });

        appDownloadManager.downloadApk(url, "标题", "qwaesdrtyguiokjytgrfredgyujiuytrewsaaesrdgyhutfrdesa");

        return appDownloadManager;
    }
}
