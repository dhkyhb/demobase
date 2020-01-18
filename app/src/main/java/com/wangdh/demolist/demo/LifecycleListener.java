package com.wangdh.demolist.demo;

import java.util.EventListener;

/**
 * Created by wangdh on 2017/4/24.
 * name：
 * 描述：
 */

public interface LifecycleListener extends EventListener {
    void lifecycleEvent(LifecycleEvent paramLifecycleEvent);
}
