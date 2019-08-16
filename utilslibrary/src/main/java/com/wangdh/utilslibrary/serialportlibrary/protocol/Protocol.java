package com.wangdh.utilslibrary.serialportlibrary.protocol;


import com.wangdh.utilslibrary.exception.AppException;

import java.util.Map;

/**
 * 硬件协议
 */
public interface Protocol {
    /**
     * 数据打包
     */
    byte[] packed(Map map) throws AppException;

    /**
     * 数据解包
     */
    void unpacking(byte[] msg) throws AppException;

    /**
     * 获取协议中保存的数据
     *
     * @return
     */
    Map getProtocolData();

    String getID();
}
