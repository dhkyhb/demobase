package com.wangdh.demolist.presenter;

import com.wangdh.demolist.annotation.MenuAnnotation;
import com.wangdh.demolist.base.mvp.BasePresenter;
import com.wangdh.demolist.entity.MenuBean;
import com.wangdh.demolist.presenter.demolist.BaseMenuPresenter;
import com.wangdh.demolist.ui.iview.IMenuView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdh on 2016/11/21.
 * name：
 * 描述：测试案例 类  传进来以后...
 */

public class MenuPresenter extends BasePresenter<IMenuView> {
    public <T> void run(Class<?> c) throws Exception {
        Class<?> aClass1 = Class.forName(c.getName(), false, c.getClassLoader());

        final T o = (T) aClass1.newInstance();
        if (o instanceof BaseMenuPresenter) {
            ((BaseMenuPresenter) o).setiMenuView(iView);
        }
        final Method[] d = o.getClass().getDeclaredMethods();
        final List<MenuBean> menuBeen = new ArrayList<>();
        for (Method method : d) {
            MenuAnnotation annotation = method.getAnnotation(MenuAnnotation.class);
            if (annotation != null) {
                MenuBean menuBean = new MenuBean();
                menuBean.setName(annotation.name());
                menuBean.setId(annotation.id());
                menuBeen.add(menuBean);
            }
        }
        if (menuBeen == null || menuBeen.size() <= 0) {
            return;
        }
//        MenuAdapter menuAdapter = new MenuAdapter();
//        menuAdapter.setIView(iView);
//        menuAdapter.setList(menuBeen);
//        menuAdapter.setClickItem(new MenuAdapter.MyClickItem() {
//            @Override
//            public void onclick(MenuBean bean) {
//                for (Method method : d) {
//                    System.out.println(method.getName());
//                    MenuAnnotation annotation = method.getAnnotation(MenuAnnotation.class);
//                    if (annotation != null) {
//                        if (bean.getId() == annotation.id()) {
//                            try {
//                                method.invoke(o);
//                            } catch (IllegalAccessException e) {
//                                e.printStackTrace();
//                            } catch (InvocationTargetException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            }
//        });
//        iView.setAdapter(menuAdapter);
    }
}
