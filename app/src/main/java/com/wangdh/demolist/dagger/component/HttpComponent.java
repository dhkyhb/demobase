package com.wangdh.demolist.dagger.component;

import com.wangdh.demolist.MainActivity;

/**
 * Created by wangdh on 2018/4/18.
 */

//@Component(modules = HttpModule.class)
public interface HttpComponent {
    void inject(MainActivity mainActivity);
}
