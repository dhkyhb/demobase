package com.wangdh.utilslibrary.exception;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

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
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public AppException(String errorMsg) {
        super(errorMsg);
        this.errorCode = defErrorCode;
        this.errorMsg = errorMsg;
    }

    public AppException(Throwable e) {
        super(e);
        if (e instanceof AppException) {
            AppException e1 = (AppException) e;
            this.errorCode = e1.getErrorCode();
            this.errorMsg = e1.getErrorMsg();
        } else if (e instanceof ConnectException) {
            this.errorCode = AppErrorCode.NET_CONNECT_ERROR.getCode();
            this.errorMsg = AppErrorCode.NET_CONNECT_ERROR.getMessage();
        } else if (e instanceof SocketTimeoutException) {
            this.errorCode = AppErrorCode.NET_CONNECT_TIMEOUT.getCode();
            this.errorMsg = AppErrorCode.NET_CONNECT_TIMEOUT.getMessage();
        } else {
            this.errorCode = sysErrorCode;
            this.errorMsg = e.getMessage();
        }

    }

    public AppException(AppErrorCode serialCheckBitError) {
        super(serialCheckBitError.getMessage());
        this.errorCode = serialCheckBitError.getCode();
        this.errorMsg = serialCheckBitError.getMessage();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
