package com.wangdh.utilslibrary.dblibrary.dao;

import com.wangdh.utilslibrary.dblibrary.DaoManager;
import com.wangdh.utilslibrary.dblibrary.build.AppVersionMsgDao;
import com.wangdh.utilslibrary.dblibrary.entity.AppVersionMsg;

public class UtilsDao {
    //添加最新版本信息
    public static void saveAppVersionMsg(AppVersionMsg bean) {
        AppVersionMsgDao dao = DaoManager.getSession().getAppVersionMsgDao();
        dao.insertOrReplace(bean);
    }

    //获取最后保存的版本信息
    public static AppVersionMsg getVersionByNew() {
        AppVersionMsgDao dao = DaoManager.getSession().getAppVersionMsgDao();
        AppVersionMsg unique = null;
        try {
            unique = dao.queryBuilder().orderDesc(AppVersionMsgDao.Properties.Mid).limit(1).unique();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unique;
    }

}
