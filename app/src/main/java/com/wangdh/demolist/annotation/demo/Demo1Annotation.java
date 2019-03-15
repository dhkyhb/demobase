package com.wangdh.demolist.annotation.demo;

import com.wangdh.demolist.annotation.TypeAnnotation;

/**
 * Created by wangdh on 2016/11/22.
 * name：
 * 描述：
 */

public class Demo1Annotation {
    @TypeAnnotation(1)
    public void set() {
        System.out.println(111);
    }
}
