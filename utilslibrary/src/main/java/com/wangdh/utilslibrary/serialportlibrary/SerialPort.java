package com.wangdh.utilslibrary.serialportlibrary;

import android.util.Log;

import com.wangdh.utilslibrary.exception.AppException;
import com.wangdh.utilslibrary.serialportlibrary.listener.LogcatListener;
import com.wangdh.utilslibrary.serialportlibrary.listener.ReadThreadStateListener;
import com.wangdh.utilslibrary.serialportlibrary.listener.SerialReadListener;
import com.wangdh.utilslibrary.utils.Wbyte;
import com.wangdh.utilslibrary.utils.logger.TLog;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SerialPort {
    public enum SerialState {
        NEW, INIT, RUN, STOP, DESTROY;
    }

    private SerialState state = SerialState.NEW;

    public void setState(SerialState msg) {
        this.state = msg;
    }

    public boolean isLog = true;
    public boolean isAutoRead = true;

    static {
        System.loadLibrary("serial-lib");
    }

    public native String stringFromJNI();

    /**
     * @param baudRate 波特率
     * @param parity   奇偶校验，0 None（默认）； 1 Odd； 2 Even
     * @param dataBits 数据位，5 ~ 8  （默认8）
     * @param stopBit  停止位，1 或 2  （默认 1）
     * @param flags    标记 0（默认）
     * @return
     * @throws SecurityException
     * @throws IOException
     */
    private native FileDescriptor open(String path, int baudRate, int parity, int dataBits,
                                       int stopBit, int flags);

    /**
     * c++ 通过本类的指针 找到 mFd  从而进行关闭
     */
    public native void close();

    private FileDescriptor mFd;
    public FileInputStream mFileInputStream;
    public FileOutputStream mFileOutputStream;

    public String name;//串口名字
    public String baudrate = "38400";//波特率
    public int dataBits = 8;//数据位，5 ~ 8  （默认8）
    public int stopBits = 1;//停止位，1 或 2  （默认 1）
    public int parity = 2;//奇偶校验，0 None（默认）； 1 Odd； 2 Even

    public boolean open(File path, String baudrate) {
        this.name = path.getPath();
        this.baudrate = baudrate;

        Log.e("getPath", path.getPath());
        Log.e("getAbsolutePath", path.getAbsolutePath());
        try {
            chmod777(path);

            mFd = open(path.getAbsolutePath(), Integer.valueOf(baudrate), parity, dataBits, stopBits, 0);
            mFileInputStream = new FileInputStream(mFd);
            mFileOutputStream = new FileOutputStream(mFd);
            setLog("串口打开成功" + " 数据位:" + dataBits + " 停止位:" + stopBits + " 校验位:" + parity);
            setState(SerialState.INIT);
            if (isAutoRead) {
                startResponseThread();
            }
            return true;
        } catch (AppException e) {
            setLog("串口打开失败" + " 数据位:" + dataBits + " 停止位:" + stopBits + " 校验位:" + parity);
            if (this.readListener != null) {
                this.readListener.error(e);
            }
            return false;
        }
    }

    public void closeSerial() {
        try {
            close();
        } catch (Exception e) {
        }
        destroy();
    }

    /**
     * 文件设置最高权限 777 可读 可写 可执行
     *
     * @param file 文件
     * @return 权限修改是否成功
     */
    void chmod777(File file) throws AppException {
        if (null == file || !file.exists()) {
            throw new AppException("文件不存在");
        }
        if (file.canRead() && file.canWrite()) {
            return;
        }
        try {
            // 获取ROOT权限
            Process su = Runtime.getRuntime().exec("/system/xbin/su");
            // 修改文件属性为 [可读 可写 可执行]
            String cmd = "chmod 777 " + file.getAbsolutePath() + "\n" + "exit\n";
            su.getOutputStream().write(cmd.getBytes());
            if (0 == su.waitFor() && file.canRead() && file.canWrite() && file.canExecute()) {
                return;
            } else {
                throw new AppException("操作文件读写权限失败");
            }
        } catch (Exception e) {
            throw new AppException("没有ROOT权限,操作文件读写权限失败");
        }
    }

    public void stop() {
        setState(SerialState.STOP);
        stopResponseThread();
    }

    public void reStart() {
        if (this.state != SerialState.STOP) {
            //除停止以外的状态都无法重新启动
            return;
        }
        startResponseThread();
    }

    //销毁
    public void destroy() {
        setState(SerialState.DESTROY);
        try {
            this.close();
            setLog("串口关闭");
        } catch (Exception e) {
        }

        if (subscribe != null) {
            subscribe.dispose();
            emitter = null;
            subscribe = null;
        }
        stopResponseThread();
        if (null != mFileInputStream) {
            try {
                mFileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mFileInputStream = null;
        }
        if (null != mFileOutputStream) {
            try {
                mFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mFileOutputStream = null;
        }
        mFd = null;
    }

    //串口监听只有一个，如想要多个在业务层进行监听
    private SerialReadListener readListener;
    private SerialReadThread serialReadThread;

    //设置读取监听
    public void setReadListener(SerialReadListener listener) {
        this.readListener = listener;
    }

    private void sendError(AppException e) {
        setLog(e.getMessage());
        if (this.readListener != null) {
            this.readListener.error(e);
        }
    }

    //启动循环读取线程
    public void startResponseThread() {
        setState(SerialState.RUN);
        stopResponseThread();
        serialReadThread = new SerialReadThread(this.mFileInputStream, this.readListener);
        serialReadThread.setStateListener(new ReadThreadStateListener() {
            @Override
            public void setState(int i) {
                stopResponseThread();
            }
        });
        serialReadThread.start();
    }

    private void stopResponseThread() {
        if (serialReadThread != null) {
            try {
                if (!serialReadThread.isInterrupted()) {
                    serialReadThread.interrupt();
                }
                serialReadThread = null;
            } catch (Exception e) {
            }
        }
    }

    public void send(byte[] msg, SerialReadListener listener) {
        setReadListener(listener);
        send(msg);
    }

    public void send(byte[] msg) {
        if (mFileOutputStream != null) {
            try {
                setLog("发送数据：" + Wbyte.bcdToString(msg));
                this.mFileOutputStream.write(msg);
            } catch (Exception e) {
                e.printStackTrace();
                sendError(new AppException("发送数据失败"));
            }
        } else {
            sendError(new AppException("串口未打开"));
        }
    }

    public byte[] response() {
        byte[] mReadBuffer = new byte[1024];
        int size = 0;
        try {
            size = this.mFileInputStream.read(mReadBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (-1 == size || 0 >= size) {
            setLog("读取数据大小" + size);
            return null;
        }
        byte[] readBytes = new byte[size];
        System.arraycopy(mReadBuffer, 0, readBytes, 0, size);
        setLog("读取到了" + new String(readBytes));
        return readBytes;
    }

    private LogcatListener logcatListener;

    public void setLogcatListen(LogcatListener logcatListener) {
        this.logcatListener = logcatListener;
        initLogcatThread();
    }

    private void setLog(String msg) {
        TLog.e(this.name + ";" + this.baudrate + ":" + msg);
        if (emitter != null && isLog) {
            msg = this.name + ";" + this.baudrate + ":" + msg;
            emitter.onNext(msg);
        }
    }

    private ObservableEmitter<String> emitter;
    private Disposable subscribe;

    private void initLogcatThread() {
        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                SerialPort.this.emitter = emitter;
            }
        });
        subscribe = stringObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        if (logcatListener == null) {
                            System.out.println(s);
                        } else {
                            logcatListener.rev(s);
                        }
                    }
                });
    }

}
