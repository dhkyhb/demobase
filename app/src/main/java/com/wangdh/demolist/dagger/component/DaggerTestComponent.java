package com.wangdh.demolist.dagger.component;

import com.wangdh.demolist.dagger.DaggerTest2;
import com.wangdh.demolist.dagger.module.HttpModule;

import dagger.Component;

/**
 * Created by wangdh on 2018/4/19.
 */
@Component(modules = HttpModule.class)
//@Component
public interface DaggerTestComponent {
    void inject(DaggerTest2 object);
}
