package com.wangdh.netlibrary.server.weather;

class Wid {

    private String day;
    private String night;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    @Override
    public String toString() {
        return "Wid{" +
                "day='" + day + '\'' +
                ", night='" + night + '\'' +
                '}';
    }
}
