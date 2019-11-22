package com.wangdh.utilslibrary.exception;

public enum AppErrorCode {
    SUCCESS("1", "成功"),
    DEF_ERROR("100001", "未定义的错误"),
    //    网络错误
    NET_CONNECT_ERROR("110001", "无法连接到服务器"),
    NET_CONNECT_TIMEOUT("110002", "网络连接超时");

    private String code;
    private String message;

    AppErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
