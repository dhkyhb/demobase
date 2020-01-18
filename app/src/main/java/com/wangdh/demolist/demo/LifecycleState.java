package com.wangdh.demolist.demo;

/**
 * Created by wangdh on 2017/4/24.
 * name：
 * 描述：生命周期状态
 */

public enum LifecycleState {
    NEW(false, null),
    INITIALIZING(false, Lifecycle.BEFORE_INIT_EVENT),
    INITIALIZED(false, Lifecycle.AFTER_INIT_EVENT),
    STARTING_PREP(false, Lifecycle.BEFORE_START_EVENT),
    STARTING(true, Lifecycle.START_EVENT), // available = true,lifecycleEvent="start"
    STARTED(true, Lifecycle.AFTER_START_EVENT),// available = true,lifecycleEvent="after_start"
    STOPPING_PREP(true, Lifecycle.BEFORE_STOP_EVENT),// available = true,lifecycleEvent="before_stop"
    STOPPING(false, Lifecycle.STOP_EVENT),
    STOPPED(false, Lifecycle.AFTER_STOP_EVENT),
    DESTROYING(false, Lifecycle.BEFORE_DESTROY_EVENT),
    DESTROYED(false, Lifecycle.AFTER_DESTROY_EVENT),
    FAILED(false, null);

    private boolean available;
    private String lifecycleEvent;

    LifecycleState(boolean available, String lifecycleEvent) {
        this.available = available;
        this.lifecycleEvent = lifecycleEvent;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public String getLifecycleEvent() {
        return this.lifecycleEvent;
    }
}