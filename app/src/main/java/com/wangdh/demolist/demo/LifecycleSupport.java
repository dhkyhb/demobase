package com.wangdh.demolist.demo;

/**
 * Created by wangdh on 2017/4/24.
 * name：
 * 描述：状态分发器
 */

public class LifecycleSupport {
    //事件源
    private Lifecycle lifecycle = null;

    private LifecycleListener[] listeners = new LifecycleListener[0];

    private final Object listenersLock = new Object();

    public LifecycleSupport(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    public void addLifecycleListener(LifecycleListener listener) {
        synchronized (this.listenersLock) {
            LifecycleListener[] results = new LifecycleListener[this.listeners.length + 1];

            for (int i = 0; i < this.listeners.length; ++i) {
                results[i] = this.listeners[i];
            }
            results[this.listeners.length] = listener;
            this.listeners = results;
        }
    }

    public LifecycleListener[] findLifecycleListeners() {
        return this.listeners;
    }

    public void fireLifecycleEvent(String type, Object data) {
        LifecycleEvent event = new LifecycleEvent(this.lifecycle, type, data);
        LifecycleListener[] interested = this.listeners;
        for (int i = 0; i < interested.length; ++i) {
            interested[i].lifecycleEvent(event);
        }
    }

    public void removeLifecycleListener(LifecycleListener listener) {
        synchronized (this.listenersLock) {
            int n = -1;
            for (int i = 0; i < this.listeners.length; ++i) {
                if (this.listeners[i] == listener) {
                    n = i;
                    break;
                }
            }
            if (n < 0) {
                return;
            }
            LifecycleListener[] results = new LifecycleListener[this.listeners.length - 1];

            int j = 0;
            for (int i = 0; i < this.listeners.length; ++i) {
                if (i != n) {
                    results[(j++)] = this.listeners[i];
                }
            }
            this.listeners = results;
        }
    }
}
