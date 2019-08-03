package com.wangdh.utilslibrary.dblibrary;

import com.wangdh.utilslibrary.dblibrary.db.DaoUser;
import com.wangdh.utilslibrary.dblibrary.entity.User;
import com.wangdh.utilslibrary.utils.logger.TLog;

import java.util.List;

/**
 * @author wangdh
 * @time 2019/2/19 16:21
 * @describe 测试
 */
public class DBTest {
    public static void insert() {
        User user = new User();
        user.name = "Andrew Grosner";
        user.age = 27;
        DaoManager.getSession().getUserDao().insert(user);

        List<User> users = DaoUser.getInstance().select();
        for (User user1 : users) {
            TLog.e(user1.toString());
        }
    }

}