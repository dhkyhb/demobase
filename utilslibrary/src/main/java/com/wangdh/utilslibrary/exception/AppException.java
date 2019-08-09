package com.wangdh.utilslibrary.exception;

/**
 * @author wangdh
 * @time 2019/5/15 16:43
 * @describe
 */
public class AppException extends Throwable {
    private String errorCode;
    private String errorMsg;

    public static final String defErrorCode = "default";
    public static final String sysErrorCode = "system";

    public AppException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public AppException(String errorMsg) {
        this.errorCode = defErrorCode;
        this.errorMsg = errorMsg;
    }

    public AppException(Throwable e) {
        this.errorCode = sysErrorCode;
        this.errorMsg = e.getMessage();
    }

}
