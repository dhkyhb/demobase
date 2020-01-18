package com.wangdh.demolist.demo;


import com.wangdh.utilslibrary.exception.AppException;

/**
 * Created by wangdh on 2017/4/17.
 * name：
 * 描述： 所有模块的生命周期
 */

public interface Lifecycle {
    public static final String BEFORE_INIT_EVENT = "before_init";
    public static final String AFTER_INIT_EVENT = "after_init";
    public static final String START_EVENT = "start";
    public static final String BEFORE_START_EVENT = "before_start";
    public static final String AFTER_START_EVENT = "after_start";
    public static final String STOP_EVENT = "stop";
    public static final String BEFORE_STOP_EVENT = "before_stop";
    public static final String AFTER_STOP_EVENT = "after_stop";
    public static final String AFTER_DESTROY_EVENT = "after_destroy";
    public static final String BEFORE_DESTROY_EVENT = "before_destroy";
    public static final String PERIODIC_EVENT = "periodic";
    public static final String CONFIGURE_START_EVENT = "configure_start";
    public static final String CONFIGURE_STOP_EVENT = "configure_stop";

    void init() throws AppException;

    void start() throws AppException;

    void stop() throws AppException;

    void destroy() throws AppException;

    void addLifecycleListener(LifecycleListener paramLifecycleListener);

    LifecycleListener[] findLifecycleListeners();

    void removeLifecycleListener(LifecycleListener paramLifecycleListener);

    LifecycleState getState();

    String getStateName();

}
