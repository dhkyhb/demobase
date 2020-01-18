package com.wangdh.utilslibrary.exception;
/**
 * @author  wangdh
 * @date 2020/1/18 11:46
 * 描述:
 */
public enum AppErrorCode {
    SUCCESS("1", "成功"),
    DEF_ERROR("100001", "未定义的错误"),
    //    网络错误
    NET_CONNECT_ERROR("110001", "无法连接到服务器"),
    NET_CONNECT_TIMEOUT("110002", "网络连接超时"),

    //串口
    SERIAL_NOT_FIND_ERROR("120001", "未发现串口"),
    SERIAL_OPEN_ERROR("120002", "打开串口失败"),
    SERIAL_DEND_ERROR("120003", "串口发送数据失败"),
    SERIAL_TIME_OUT_ERROR("120004", "串口连接超时"),
    SERIAL_NOT_START("120005", "串口程序未启动"),

    SERIAL_DATE_PACKED_ERROR("120011", "数据打包失败"),
    SERIAL_DATE_UNPACKE_ERROR("120012", "数据解包失败"),
    SERIAL_DATE_CHECK_ERROR("120013", "数据校验失败"),
    SERIAL_DATE_LENGHT_ERROR("120014", "长度校验失败"),
    SERIAL_DATE_TYPE_ERROR("120015", "请求信息与接收信息不匹配"),

    SERIAL_LEN_ERROR("120021","字节码和发出的字节数不相符"),
    SERIAL_CRC("120022","CRC校验不正确"),
    SERIAL_OUT("120023","货道号超出范围（Yn、Xn可能超出)"),
    SERIAL_NOT_CODE("120024","没有对应的功能码"),
    SERIAL_NOT_STATE("120025","没有对应的模式"),
    SERIAL_CHECK_BIT_ERROR("120026","起始码或者停止码不对"),
    SERIAL_NOT_RUN("120027","未掉货"),

    LIFECYCLE_STATE("900001","生命周期状态异常")
    ;

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
