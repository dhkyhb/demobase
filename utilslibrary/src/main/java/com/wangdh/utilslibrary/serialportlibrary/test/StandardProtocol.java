package com.wangdh.utilslibrary.serialportlibrary.test;

import com.wangdh.utilslibrary.exception.AppErrorCode;
import com.wangdh.utilslibrary.exception.AppException;
import com.wangdh.utilslibrary.serialportlibrary.SerialPortManager;
import com.wangdh.utilslibrary.serialportlibrary.listener.SerialProtocolListener;
import com.wangdh.utilslibrary.serialportlibrary.protocol.ProtocolInterface;
import com.wangdh.utilslibrary.utils.Wbyte;
import com.wangdh.utilslibrary.utils.TLog;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public abstract class StandardProtocol implements ProtocolInterface {
    //是否是一次性的
    public boolean single = true;
    public byte[] responseData;
    public String parentId = "要设置成串口的唯一值";

    public long outTime = 10;

    public Map data = new HashMap();

    private SerialProtocolListener serialProtocolListener;

    public SerialProtocolListener getSerialProtocolListener() {
        return serialProtocolListener;
    }

    public void setSerialProtocolListener(SerialProtocolListener serialProtocolListener) {
        this.serialProtocolListener = serialProtocolListener;
    }

    @Override
    public void putData(Map map) {
        data.putAll(map);
    }

    @Override
    public byte[] getProtocol() {
        try {
            byte[] bytes = dataPack(data);
            TLog.e("发送数据:" + Wbyte.bcdToString(bytes));
            beginSend();
            return bytes;
        } catch (AppException e) {
            e.printStackTrace();
            failed(data, e);
        } catch (Exception e) {
            e.printStackTrace();
            failed(data, new AppException(e.getMessage()));
        }
        return null;
    }

    @Override
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    private static Disposable mDisposable;

    //发送的时候开始倒计时
    protected void beginSend() {
        addManage();
        mDisposable = Flowable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        if (outTime <= aLong) {
                            mDisposable.dispose();
                            failed(data, new AppException(AppErrorCode.SERIAL_TIME_OUT_ERROR));
                        }
                    }
                });
    }

    //结束
    @Override
    public void destory() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
        SerialPortManager.remove(parentId, this);
    }

    public void addManage() {
        SerialPortManager.add(parentId, this);
    }

    @Override
    public boolean responseProtocol(byte[] msg) {
        try {
            checkProtoclo(msg);
        } catch (AppException e) {
            e.printStackTrace();
            return false;
        }
        this.responseData = msg;
        boolean b = matchingProtoclo();
        TLog.e("返回数据是否匹配这个报文：" + (b ? "是" : "否"));
        if (!b) {
            return false;
        }
        try {
            dataUnPack(msg);
        } catch (AppException e) {
            e.printStackTrace();
            failed(data, e);
        }
        return true;
    }

    public void success(Map data) {
        TLog.e("success:");
        destory();
        if (serialProtocolListener != null) {
            serialProtocolListener.success(data);
            if (single) {
                serialProtocolListener = null;
            }
        }
    }

    public void failed(Map data, AppException e) {
        TLog.e("failed:"+e.getErrorMsg());
        destory();
        if (serialProtocolListener != null) {
            serialProtocolListener.failed(data, e);
            if (single) {
                serialProtocolListener = null;
            }
        }
    }
}
