package com.wangdh.demolist.demo;

import java.util.EventObject;

/**
 * Created by wangdh on 2017/4/24.
 * name：
 * 描述：
 */

public final class LifecycleEvent extends EventObject {
    private static final long serialVersionUID = 1L;
    private Object data = null;

    private String type = null;

    public LifecycleEvent(Lifecycle lifecycle, String type, Object data) {
        super(lifecycle);
        this.type = type;
        this.data = data;
    }

    public Object getData() {
        return this.data;
    }

    public Lifecycle getLifecycle() {
        return ((Lifecycle) getSource());
    }

    public String getType() {
        return this.type;
    }
}