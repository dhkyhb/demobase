package com.wangdh.dblibrary;

import android.content.Context;


/**
 * @author wangdh
 * @time 2019/2/19 15:10
 * @describe
 */
public class DBLibrary {
    public static void init(Context context) {
        DaoManager.init(context);
    }
}
