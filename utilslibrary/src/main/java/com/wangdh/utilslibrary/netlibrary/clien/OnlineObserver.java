package com.wangdh.utilslibrary.netlibrary.clien;

import io.reactivex.observers.DisposableObserver;

/**
 * @author wangdh
 * @time 2019/5/15 17:08
 * @describe
 */
public abstract class OnlineObserver<T> extends DisposableObserver<T> {
    protected OnlineListener listener;
    protected OnlineContext context;

    public OnlineListener getListener() {
        return listener;
    }

    public void setListener(OnlineListener listener) {
        this.listener = listener;
    }

    public OnlineContext getContext() {
        return context;
    }

    public void setContext(OnlineContext context) {
        this.context = context;
    }

}
