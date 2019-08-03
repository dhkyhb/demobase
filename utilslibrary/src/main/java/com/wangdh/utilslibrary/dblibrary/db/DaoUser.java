package com.wangdh.utilslibrary.dblibrary.db;



import com.wangdh.utilslibrary.dblibrary.DaoManager;
import com.wangdh.utilslibrary.dblibrary.build.UserDao;
import com.wangdh.utilslibrary.dblibrary.entity.User;

import java.util.List;

/**
 * @author wangdh
 * @time 2019/2/20 14:19
 * @describe
 */
public class DaoUser {
    private static DaoUser daoUser;

    private UserDao userDao;

    public DaoUser() {
        userDao = DaoManager.getSession().getUserDao();
    }

    public static DaoUser getInstance() {
        //单例锁是为了避免重复创建
        synchronized (DaoUser.class) {
            if (daoUser == null) {
                daoUser = new DaoUser();
            }
        }
        return daoUser;
    }

    public void insert(User user) {
        userDao.insert(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public List<User> select() {
        List<User> users = userDao.loadAll();
        return users;
    }


}
