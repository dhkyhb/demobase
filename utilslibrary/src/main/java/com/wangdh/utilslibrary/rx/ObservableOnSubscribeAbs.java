package com.wangdh.utilslibrary.rx;

import java.util.Map;

import io.reactivex.ObservableOnSubscribe;

/**
 * @author wangdh
 * @time 2019/8/5 17:18
 * @describe
 */
public abstract class ObservableOnSubscribeAbs<T> implements ObservableOnSubscribe<T> {
    private Map<String, Object> data;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
