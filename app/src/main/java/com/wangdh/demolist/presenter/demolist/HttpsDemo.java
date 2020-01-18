package com.wangdh.demolist.presenter.demolist;

import android.os.AsyncTask;
import android.os.Bundle;

import com.wangdh.demolist.annotation.MenuAnnotation;
import com.wangdh.demolist.ui.fragment.TextFragment;
import com.wangdh.utilslibrary.utils.TLog;

/**
 * Created by wangdh on 2016/11/22.
 * name：
 * 描述：
 */

public class HttpsDemo extends BaseMenuPresenter {


    @MenuAnnotation(name = "连接", id = 1)
    public void demo1() {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                com.wangdh.demolist.net.https.HttpsDemo httpsDemo = new com.wangdh.demolist.net.https.HttpsDemo(getiMenuView().getContext());
                return httpsDemo.run();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Bundle bundle = new Bundle();
                bundle.putString("showData", s);

                TextFragment textFragment = new TextFragment();
                textFragment.setArguments(bundle);
                iMenuView.openFragment(textFragment);

            }
        }.execute();
    }

    @MenuAnnotation(name = "按钮2", id = 2)
    public void demo2() {
        Bundle bundle = new Bundle();
        bundle.putString("showData", "没什么用的！");

        TextFragment textFragment = new TextFragment();
        textFragment.setArguments(bundle);
        iMenuView.openFragment(textFragment);
    }

    @MenuAnnotation(name = "按钮3", id = 2)
    public void demo3() {
        TLog.e("ewerwe");
    }
}
