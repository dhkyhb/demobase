package com.wangdh.utilslibrary.serialportlibrary.protocol;

import com.wangdh.utilslibrary.exception.AppException;
import com.wangdh.utilslibrary.serialportlibrary.listener.SerialProtocolListener;

import java.util.Map;

//解码协议接口
public interface ProtocolInterface {

    //校验协议的准确性
    void checkProtoclo(byte[] data) throws AppException;

    /**
     * 判断返回的是否是和当前协议匹配的协议
     * 此方法 应该在最底层的协议中实现， 比如出货协议，比如初始化协议
     *
     * @return
     */
    boolean matchingProtoclo();

    void putData(Map map);

    /**
     * 获取协议报文,执行此方法时，将会把这个协议对象保存到 manage里面便于数据返回
     *
     * @return
     */
    byte[] getProtocol();

    /**
     * 接受串口返回的数据
     *
     * @param msg
     * @return 是否消费了 协议
     */
    boolean responseProtocol(byte[] msg);

    /**
     * 设置这个协议的 串口id 便于销毁
     *
     * @param parentId 连接的串口的唯一值， 可以是串口名
     */
    void setParentId(String parentId);

    byte[] dataPack(Map map) throws AppException;

    void dataUnPack(byte[] msg) throws AppException;

    SerialProtocolListener getSerialProtocolListener();

    void setSerialProtocolListener(SerialProtocolListener serialProtocolListener);

    void destory();
}
