package com.wangdh.demolist.annotation;

import org.greenrobot.greendao.annotation.apihint.Internal;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wangdh on 2016/11/22.
 * name：
 * 描述：
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Internal
public @interface MenuAnnotation {
    String name() default "功能";

    int id() default 0;
}
