package com.wangdh.demolist.dagger.entity;

import javax.inject.Inject;

/**
 * Created by wangdh on 2018/4/18.
 */

public class Product {
    @Inject
    public Product() {
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                '}';
    }
}
