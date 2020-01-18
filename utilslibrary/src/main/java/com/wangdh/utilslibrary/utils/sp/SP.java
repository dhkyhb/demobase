package com.wangdh.utilslibrary.utils.sp;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author  wangdh
 * @date 2020/1/18 11:18
 * 描述:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SP {
    String hint() default ""; //显示的中文应该使用android id  int类型 完成中英文转换，后期如果有需求新增或者修改返回值

    int hintId() default 0;

    String value();

    Class<?> type();

    boolean change() default true;

    boolean isShow() default true;

    String[] selectValue() default {};

    int[] selectValueId() default {};

    String[] selectKey() default {};

    String pattern() default "";

    String error() default "";

    int errorId() default 0;

    int lenght() default 0;

    //https://www.crifan.com/summary_android_edittext_inputtype_values_and_meaning_definition/
    int inputType() default -1;

    String number() default "";//编号 ，解决下发参数自动注入

    String oldKey() default "";//兼容以前
}
