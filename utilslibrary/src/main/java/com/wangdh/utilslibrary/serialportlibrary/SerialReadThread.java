package com.wangdh.utilslibrary.serialportlibrary;

import com.wangdh.utilslibrary.exception.AppException;
import com.wangdh.utilslibrary.serialportlibrary.listener.ReadThreadStateListener;
import com.wangdh.utilslibrary.serialportlibrary.listener.SerialReadListener;
import com.wangdh.utilslibrary.utils.TLog;

import java.io.FileInputStream;

/**
 * 读取的循环线程
 */
public class SerialReadThread extends Thread {
    private FileInputStream inputStream;
    private SerialReadListener readListener;
    private ReadThreadStateListener stateListener;

    private byte[] mReadBuffer = null;

    SerialReadThread(FileInputStream stream, SerialReadListener listener) {
        this.inputStream = stream;
        TLog.e("设置：" + readListener);
        this.readListener = listener;
    }

    public void setReadListener(SerialReadListener readListener) {
        this.readListener = readListener;
    }

    @Override
    public void run() {
        super.run();
        while (!this.isInterrupted()) {
//            if (inputStream == null || readListener == null) {
//                return;
//            }
            try {
                int size = 0;
                mReadBuffer = new byte[255];
                size = inputStream.read(mReadBuffer);
                if (-1 == size || 0 >= size) {
                    continue;
                }
                byte[] readBytes = new byte[size];
                System.arraycopy(mReadBuffer, 0, readBytes, 0, size);
//                TLog.e((readListener == null) + "线程读取：" + Wbyte.bcdToString(readBytes));
                if (readListener != null) {
//                    byte[] testre = Wbyte.stringToBcd("FF0E00A40B010101030F0F6D54FE");
//                    readListener.read(testre);
                    readListener.read(readBytes);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (!this.isInterrupted()) {
                    //如果不是主动关闭导致的异常 ，将通知给被调用 的类。
                    if (readListener != null) {
                        readListener.error(new AppException("数据读取异常，读取线程终止"));
                    }
                } else {
                    //是主动关闭 导致的异常 ，则不管。
                }
            }
        }
        if (this.stateListener != null) {
            this.stateListener.setState(0);
            this.stateListener = null;
        }
    }

    public void setStateListener(ReadThreadStateListener listener) {
        this.stateListener = listener;
    }

    @Override
    public void interrupt() {
        this.inputStream = null;
        this.readListener = null;
        super.interrupt();
    }
}
