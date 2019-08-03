package com.wangdh.utilslibrary.netlibrary.test;

/**
 * @author wangdh
 * @time 2019/5/13 16:32
 * @describe 附加属性
 */
public class BeanAddition {
    private String imei;
    private String brand;//手机品牌
    private String model;//手机型号
    private String sdk_int;//手机sdk版本
    private String release;//手机android 版本

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSdk_int() {
        return sdk_int;
    }

    public void setSdk_int(String sdk_int) {
        this.sdk_int = sdk_int;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    @Override
    public String toString() {
        return "BeanAddition{" +
                "imei='" + imei + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", sdk_int='" + sdk_int + '\'' +
                ", release='" + release + '\'' +
                '}';
    }
}
