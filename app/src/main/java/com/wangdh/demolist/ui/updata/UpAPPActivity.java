package com.wangdh.demolist.ui.updata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wangdh.demolist.R;
import com.wangdh.demolist.utils.updata.AppDownloadManager;
import com.wangdh.demolist.utils.updata.UpdataTest;

public class UpAPPActivity extends AppCompatActivity {
    private AppDownloadManager test = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_app);
        test = UpdataTest.test(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        test.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        test.onPause();
    }
}
