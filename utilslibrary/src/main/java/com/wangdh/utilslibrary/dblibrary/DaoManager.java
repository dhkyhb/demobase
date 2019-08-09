package com.wangdh.utilslibrary.dblibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wangdh.utilslibrary.dblibrary.build.DaoMaster;
import com.wangdh.utilslibrary.dblibrary.build.DaoSession;

import org.greenrobot.greendao.database.Database;


/**
 * 剪切到主程序去
 * Created by wangdh on 2017/11/8.
 * name：
 * 描述：
 */
public class DaoManager {
    private static final String TAG = DaoManager.class.getSimpleName();

    private static Context context = null;

    private static String dbName = "libDB";

    private static DaoMaster.DevOpenHelper openHelper;

    private static DaoSession daoSession = null;

    public static DaoSession getSession() {
        if (context == null) {
            Log.e(TAG, "greendao 数据库上下文未初始化！请调用init(Context context)");
            return null;
        }
        if (daoSession != null) {
            return daoSession;
        }

        Database db = getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        return daoSession;
    }

    //切换数据库
    public static void switchDB(String name) {
        openHelper = new DaoMaster.DevOpenHelper(context, name, null);
        Database db = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }


    public static void init(Context contextz) {
        context = contextz;
    }

    /**
     * 获取可读数据库
     */
    private static SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private static Database getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
//        SQLiteDatabase db = openHelper.getWritableDatabase();
        Database db = openHelper.getWritableDb();
        return db;
    }

}
