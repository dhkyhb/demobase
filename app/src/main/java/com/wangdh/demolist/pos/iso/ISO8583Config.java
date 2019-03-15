package com.wangdh.demolist.pos.iso;

/**
 * Created by wangdh on 2018/5/19.
 */

public @interface ISO8583Config {
    int id();//序列化用

    String name(); //域号

    int bodyLen() default 0; //域的长度，

    String bodyType();//域属性 ASC BCD BIN

    String lenType() default "BCD"; //长度属性 ASC BCD BIN

    int lenLenght() default 0; //0 定长  ，1~3 变长

    int lenRadix() default 10; //默认10进制

    String completion() default "0";    //补全,补空格或者0 等等

    String align() default "";    //左右靠
}
