package com.wangdh.utilslibrary.serialportlibrary.listener;

import com.wangdh.utilslibrary.exception.AppException;

/**
 * 串口数据读取监听
 */
public interface SerialReadListener {
    void read(byte[] msg);

    void error(AppException e);
}
