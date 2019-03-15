package com.wangdh.demolist.base.mvp;

/**
 * Created by wangdh on 2016/11/16.
 * name：
 * 描述：persenter 父类
 */

public class BasePresenter<T> {
    protected T iView;

    public T getiView() {
        return iView;
    }

    public void setiView(T iView) {
        this.iView = iView;
    }
}
