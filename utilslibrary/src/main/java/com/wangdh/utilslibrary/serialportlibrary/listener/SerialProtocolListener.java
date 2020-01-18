package com.wangdh.utilslibrary.serialportlibrary.listener;

import com.wangdh.utilslibrary.exception.AppException;

import java.util.Map;

public interface SerialProtocolListener {
    void success(Map data);

    void failed(Map data, AppException e);
}
