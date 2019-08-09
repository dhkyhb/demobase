package com.wangdh.utilslibrary.dblibrary.db;

import com.wangdh.utilslibrary.dblibrary.DaoManager;
import com.wangdh.utilslibrary.dblibrary.build.LogTextDao;
import com.wangdh.utilslibrary.dblibrary.entity.LogText;

import java.util.List;

/**
 * @author wangdh
 * @time 2019/8/5 15:49
 * @describe
 */
public class SQL_LogText {
    private static SQL_LogText bean;
    private LogTextDao dao;

    public SQL_LogText() {
        dao = DaoManager.getSession().getLogTextDao();
    }

    public synchronized static SQL_LogText getInstance() {
        //单例锁是为了避免重复创建
        if (bean == null) {
            bean = new SQL_LogText();
        }
        return bean;
    }

    public void insert(LogText user) {
        dao.insert(user);
    }

    public void delete(LogText user) {
        dao.delete(user);
    }

    public void update(LogText user) {
        dao.update(user);
    }

    //大量数据的插入
    public void insert(final List<LogText> datas) {
        DaoManager.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (LogText user : datas) {
                    dao.insert(user);
                }
            }
        });
    }

    public List<LogText> select() {
        List<LogText> users = dao.loadAll();
        return users;
    }
}
