package com.wangdh.netlibrary.server.weather;

import java.util.Date;

class Future {

    private Date date;
    private String temperature;
    private String weather;
    private Wid wid;
    private String direct;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Wid getWid() {
        return wid;
    }

    public void setWid(Wid wid) {
        this.wid = wid;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    @Override
    public String toString() {
        return "Future{" +
                "date=" + date +
                ", temperature='" + temperature + '\'' +
                ", weather='" + weather + '\'' +
                ", wid=" + wid +
                ", direct='" + direct + '\'' +
                '}';
    }
}
