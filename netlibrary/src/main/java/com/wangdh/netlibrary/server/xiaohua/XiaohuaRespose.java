package com.wangdh.netlibrary.server.xiaohua;

/**
 * @author wangdh
 * @time 2019/5/13 17:29
 * @describe
 */
public class XiaohuaRespose<T> {
    private String reason;
    private int error_code;
    private T result;

    @Override
    public String toString() {
        return "XiaohuaRespose{" +
                "reason='" + reason + '\'' +
                ", error_code=" + error_code +
                ", body=" + result +
                '}';
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public T getBody() {
        return result;
    }

    public void setBody(T body) {
        this.result = body;
    }
}
