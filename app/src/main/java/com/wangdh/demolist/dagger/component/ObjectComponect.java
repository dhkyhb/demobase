package com.wangdh.demolist.dagger.component;

/**
 * Created by wangdh on 2018/4/19.
 * 经过测试，目标接受类 必须是一个准确的（非子类，接口类等），并且带有 @Inject 属性的一个实现类
 * 无法是Object 泛型，等。
 */
//@Component
public interface ObjectComponect {
    void inject(Object object);
}
