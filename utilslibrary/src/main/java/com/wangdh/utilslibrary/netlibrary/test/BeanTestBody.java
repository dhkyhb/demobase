package com.wangdh.utilslibrary.netlibrary.test;

import java.util.List;

/**
 * @author wangdh
 * @time 2019/5/13 16:41
 * @describe
 */
public class BeanTestBody {
    private String name;
    private List<BeanBody2> beanBody2List;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BeanBody2> getBeanBody2List() {
        return beanBody2List;
    }

    public void setBeanBody2List(List<BeanBody2> beanBody2List) {
        this.beanBody2List = beanBody2List;
    }
}
