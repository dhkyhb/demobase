package com.wangdh.utilslibrary.utils.sp;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SP {
    //2017年10月23日 10:22:01
//验证邮箱
    //"^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
//IP
    //String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    //String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";
//电话
    //"^(\\d{3,4}-)?\\d{6,8}$"
//手机
    //"^[1]+[3,5]+\\d{9}$"
// 验证输入身份证号
    //(^\\d{18}$)|(^\\d{15}$)
//验证数字输入
    //^[0-9]*$
//验证验证输入汉字
    //"^[\u4e00-\u9fa5]{0,}$"
//验证验证输入字符串
    //^.{8,}$

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
