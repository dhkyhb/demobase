package com.wangdh.demolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.wangdh.demolist.ui.aty.MenuActivity;
import com.wangdh.utilslibrary.utils.AppManager;


/**
 * @author  wangdh
 * @date 2020/1/18 14:15
 * 描述: 应用启动界面
 */
public class WellcomeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 防止第三方跳转时出现双实例
        Activity aty = AppManager.getActivity(MainActivity.class);
        if (aty != null && !aty.isFinishing()) {
            finish();
        }
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.app_start);
        findViewById(R.id.ll_start).postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectTo();
            }
        }, 500);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        int cacheVersion = PreferenceHelper.readInt(this, "first_install",
//                "first_install", -1);
//        int currentVersion = TDevice.getVersionCode();
//        if (cacheVersion < currentVersion) {
//            PreferenceHelper.write(this, "first_install", "first_install",
//                    currentVersion);
//        }
    }

    /**
     * 跳转到...
     */
    private void redirectTo() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
