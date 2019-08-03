package com.wangdh.utilslibrary.netlibrary.server.weather;

import java.util.List;

public class ResultBean {

    private String city;
    private Realtime realtime;
    private List<Future> future;

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setRealtime(Realtime realtime) {
        this.realtime = realtime;
    }

    public Realtime getRealtime() {
        return realtime;
    }

    public void setFuture(List<Future> future) {
        this.future = future;
    }

    public List<Future> getFuture() {
        return future;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "city='" + city + '\'' +
                ", realtime=" + realtime +
                ", future=" + future +
                '}';
    }
}
