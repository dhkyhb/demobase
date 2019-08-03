package com.wangdh.utilslibrary.netlibrary.test;

/**
 * @author wangdh
 * @time 2019/5/13 16:22
 * @describe
 */
public class BeanBaseHead <T>{
    private String smg;
    private String code;
    private String key;
    private String verify;
    private T obj;
    private BeanAddition addition;

    public String getSmg() {
        return smg;
    }

    public void setSmg(String smg) {
        this.smg = smg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public BeanAddition getAddition() {
        return addition;
    }

    public void setAddition(BeanAddition addition) {
        this.addition = addition;
    }

    @Override
    public String toString() {
        return "BeanBaseHead{" +
                "smg='" + smg + '\'' +
                ", code='" + code + '\'' +
                ", key='" + key + '\'' +
                ", verify='" + verify + '\'' +
                ", obj=" + obj +
                ", addition=" + addition +
                '}';
    }
}
