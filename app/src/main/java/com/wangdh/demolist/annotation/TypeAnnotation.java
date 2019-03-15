package com.wangdh.demolist.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wangdh on 2016/11/22.
 * name：
 * 描述：@Target作用：用于描述注解的使用范围（即：被描述的注解可以用在什么地方）
 */
@Target(ElementType.METHOD)
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
public @interface TypeAnnotation {
    int value();
}
